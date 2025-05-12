package edu.monash.fit2099.engine.actors;

import edu.monash.fit2099.engine.positions.Location;

import java.util.*;

/**
 * Represents a bimap of locations and actors. Hence it enforces the one actor
 * per location rule. Has a nifty iterator that lets us modify the collection
 * while iterating it.
 *
 */
public class ActorLocationsIterator implements Iterable<Actor> {
	/**
	 * A mapping of location to the actor
	 */
	private final Map<Location, Actor> locationToActor;
	/**
	 * A mapping of actor to location
	 */
	private final Map<Actor, Location> actorToLocation;
	/**
	 * Current actor
	 */
	private Actor player;

	/**
	 * Default constructor.
	 */
	public ActorLocationsIterator() {
		locationToActor = new HashMap<Location, Actor>();
		actorToLocation = new HashMap<Actor, Location>();
	}

	/**
	 * Adds an actor and identifies it as the player.
	 * The player always gets to go first in any turn.
	 * @param player the player
	 */
	public void setPlayer(Actor player) {
		this.player = player;
	}
			
			
	/**
	 * Add a new actor at the given Location.
	 *
	 * @param actor the actor to place
	 * @param location where to place the actor
	 * @throws IllegalArgumentException if the actor is already placed or there is already an actor at the target Location
	 */
	public void add(Actor actor, Location location) {
		if(actorToLocation.containsKey(actor))
			throw new IllegalArgumentException();
		if(locationToActor.containsKey(location))
			throw new IllegalArgumentException();
		
		actorToLocation.put(actor, location);
		locationToActor.put(location, actor);
	}

	/**
	 * Remove an actor from the system.
	 *
	 * @param actor the actor to remove
	 */
	public void remove(Actor actor) {
		Location location = actorToLocation.get(actor);
		actorToLocation.remove(actor);
		locationToActor.remove(location);
	}

	/**
	 * Moves an existing actor to a new Location.
	 *
	 * @param actor the actor to move
	 * @param newLocation the actor's destination
	 * @throws IllegalArgumentException if another actor is already at that Location
	 */
	public void move(Actor actor, Location newLocation) {
		if(isAnActorAt(newLocation))
			throw new IllegalArgumentException("Can't move to another actor");

		Location oldLocation = actorToLocation.get(actor);
		actorToLocation.put(actor, newLocation);
		locationToActor.remove(oldLocation);
		locationToActor.put(newLocation, actor);
	}

	/**
	 * Returns true if actor exists in the system.
	 *
	 * @param actor the actor to look for
	 * @return true if and only if actor is somewhere in the system
	 */
	public boolean contains(Actor actor) {
		return actorToLocation.containsKey(actor);
	}

	/**
	 * Returns true if an actor is at the given Location.
	 *
	 * @param location the Location to check
	 * @return true if and only if an actor is at the given Location.
	 */
	public boolean isAnActorAt(Location location) {
		return locationToActor.containsKey(location);
	}

	/**
	 * Returns a reference to the actor at the given location, if there is one.
	 *
	 * @param location the location to check
	 * @return a reference to the actor, or null if there isn't one
	 */
	public Actor getActorAt(Location location) {
		return locationToActor.get(location);
	}

	/**
	 * Returns a reference to the Location containing the given actor.
	 * @param actor the actor to look for
	 * @return the Location containing actor
	 */
	public Location locationOf(Actor actor) {
		return actorToLocation.get(actor);
	}

	/**
	 * Class to allow iterating over all Actors in the system, player first
	 * This allows Actors to take turns in a known order.
	 */
	class ActorIterator implements Iterator<Actor> {
		Map<Actor, Location> actorLocations;
		List<Actor> actors;

		/**
		 * Constructor.
		 *
		 * @param actorLocations the collection of mappings between Actors and Locations
		 */
		public ActorIterator(Map<Actor, Location> actorLocations) {
			this.actorLocations = actorLocations;
			actors = new ArrayList<Actor>(actorLocations.keySet());
			
			// Make sure the player is first. 
			if(actors.contains(player)) {
				actors.remove(player);
				actors.add(0, player);
			}
		}

		/**
		 * @see Iterator#hasNext()
		 * @return true if there is another actor that needs to take a turn, false otherwise
		 */
		@Override
		public boolean hasNext() {
			for (Actor actor : actors) {
				if (actorLocations.containsKey(actor))
					return true;
			}

			return false;
		}

		/**
		 * @see Iterator#next()
		 */
		@Override
		public Actor next() {
			while (!actors.isEmpty()) {
				Actor actor = actors.remove(0);
				if (actorLocations.containsKey(actor))
					return actor;
			}

			throw new ConcurrentModificationException();
		}
	}

	/**
	 * @see Iterable#iterator()
	 */
	@Override
	public Iterator<Actor> iterator() {
		return new ActorIterator(actorToLocation);
	}
}
