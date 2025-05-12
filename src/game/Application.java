package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actions.TeleportAction;
import game.actors.AnonymousTrader;
import game.actors.DivineBeastDancingLion;
import game.actors.FurnaceGolem;
import game.actors.Player;
import game.grounds.*;
import game.items.ConsumableItem;
import game.items.FlaskOfHealing;
import game.items.FlaskOfRejuvenation;
import game.items.ShadowTree;
import game.weapons.*;
import game.weaponarts.LifeSteal;
import game.weaponarts.QuickStep;

/**
 * The main class to start the game.
 * This class initializes the game world, the maps, the player, and different weapons and items, and
 * sets up the gates to travel between maps.
 * Created by:
 * @author Adrian Kristanto
 * Modified by: Anuj Ranjan and Mateysh Naidu
 *
 */
public class Application {
    /**
     * The entry point of the game application.
     * This method sets up the game world with different maps, actors, items, and gates, and then starts the game loop.
     *
     * @param args Command line arguments (not used)
     */

    public static void main(String[] args) {
        World world = new World(new Display());

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(),
                new Wall(), new Floor(), new Puddle(), new PoisonSwamp());
        GameMap gameMap;

        List<String> map = Arrays.asList(
                "..........~~~~~~~...~~~~~~~......~...........",
                "~..........~~~~~....~~~~~~...................",
                "~~.........~~~~.....~~~~~~...................",
                "~~~..#####..~~.....~~~~~~~...................",
                "~~~..#___#........~~~~~~~~~..................",
                "~~~..#___#.......~~~~~~.~~~..................",
                "~~~..##_##......~~~~~~.......................",
                "~~~~...........~~~~~~~...........~~..........",
                "~~~~~.........~~~~~~~~.......~~~~~~~.........",
                "~~~~~~.......~~~~~~~~~~.....~~~~~~~~.........");

        gameMap = new GameMap("Gravesite Plain", groundFactory, map);
        world.addGameMap(gameMap);

        List<String> towerMap = Arrays.asList(
                "###########........................##########",
                "#____#____#......................._____#____#",
                "#____#_.._#.#...~~~.......~~~....#____#____##",
                "###_~~____###...~~~..~~~..~~~...####______###",
                "###...____###..~~~~..~~~~..~~~...######_____#",
                "##~~###..####..~~~...~~~.....~~~..####..#####",
                "##__.....####..~~~.~~~~~..~~~....#####____###",
                "###..##..##.#..~~..~~~~~..~~~~....####~..####",
                "#....__..__.#..~~..~~~~~~..~~....__~~~~######",
                "###########....................##############");

        GameMap tower = new GameMap("Belurat, Tower Settlement", groundFactory, towerMap);
        world.addGameMap(tower);

        List<String> sewerMap = Arrays.asList(
                "##++++++#####++++++++~~~~~++++",
                "##+++++++###+++++++++~~~~~++++",
                "##++++++++++++++++++~~~~~~~++~",
                "###+++++++++++++++.~~~~~~~~.~~",
                "~~~~~.+++++~~~++++~~~~~~~~~..~",
                "~~~~~~~~~~~~~~~++++~~~~+++~...",
                "~~~~+~~~~~~~~~~+++++~~~~~~~###",
                "+~~~~++####~~~~~++++##.~++~###",
                "++~~+++#####~~~~~++###++~~~###",
                "+~~++++######~~~~++###++~~~###");

        GameMap sewer = new GameMap("Belurat Sewer", groundFactory, sewerMap);
        world.addGameMap(sewer);


        // Adding the new Stagefront map for the Divine Beast battle
        List<String> stagefrontMapLayout = Arrays.asList(
                "#################",
                "#~~~..........~~#",
                "#~~~...........~#",
                "#~~.............#",
                "#............~~~#",
                "#..........~~~~~#",
                "#######...#######"
        );
        GameMap stagefrontMap = new GameMap("Stagefront", groundFactory, stagefrontMapLayout);
        world.addGameMap(stagefrontMap);

        Player player = new Player("Tarnished", '@', 50000);
        world.addPlayer(player, gameMap.at(7, 4));

        WeaponItem shortSwordQuickStep = new ShortSword(new QuickStep());
        shortSwordQuickStep.setMeetMinimumRequirement(player);
        gameMap.at(7, 7).addItem(shortSwordQuickStep);

        WeaponItem greatKnifeLifeSteal = new GreatKnife(new LifeSteal());
        greatKnifeLifeSteal.setMeetMinimumRequirement(player);
        gameMap.at(10, 7).addItem(greatKnifeLifeSteal);

        WeaponItem greatKnifeQuickStep = new GreatKnife(new QuickStep());
        greatKnifeQuickStep.setMeetMinimumRequirement(player);
        gameMap.at(10, 8).addItem(greatKnifeQuickStep);

        Gate gate = new Gate('H', "Belurat Gate");
        gate.addSampleAction(new TeleportAction(tower.at(7,2), "Belurat, Tower Settlement"));
        gate.addSampleAction(new TeleportAction(sewer.at(7,4), "Belurat, Sewer"));
        gameMap.at(20, 9).setGround(gate);

        Gate towerGate = new Gate('H', "Gravesite Gate");
        towerGate.addSampleAction(new TeleportAction(gameMap.at(20, 9), "to Gravestite Plain!"));
        tower.at(7,2).setGround(towerGate);

        Gate sewerGate = new Gate('H', "Gravesite Gate");
        towerGate.addSampleAction(new TeleportAction(gameMap.at(20, 9), "to Gravestite Plain!"));
        sewer.at(7,4).setGround(sewerGate);

        ConsumableItem FlaskOfHealing = new FlaskOfHealing();
        gameMap.at(6, 5).addItem(FlaskOfHealing);

        ConsumableItem FlaskOfRejuvenation = new FlaskOfRejuvenation();
        gameMap.at(8, 5).addItem(FlaskOfRejuvenation);

        ConsumableItem ShadowTree = new ShadowTree();
        gameMap.at(10,9).addItem(ShadowTree);
        gameMap.at(11,9).addItem(ShadowTree);
        gameMap.at(12,9).addItem(ShadowTree);
        gameMap.at(13,9).addItem(ShadowTree);
        gameMap.at(14,9).addItem(ShadowTree);

        // Add the Divine Beast to the Stagefront map
        Display display = new Display();
        DivineBeastDancingLion divineBeast = new DivineBeastDancingLion(display, stagefrontMap, tower);
        stagefrontMap.at(5, 5).addActor(divineBeast);  // Placing the Divine Beast in the center

        // Create a gate in the Tower Settlement to the Stagefront for the Divine Beast battle
        Gate stagefrontGate = new Gate('H', "Stagefront Gate");
        stagefrontGate.addSampleAction(new TeleportAction(stagefrontMap.at(5, 1), "to Stagefront!"));
        tower.at(7, 0).setGround(stagefrontGate); // Add the gate in Tower Settlement
        // BEHOLD, ELDEN THING!
        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        gameMap.at(25, 8).addActor(new FurnaceGolem(new Display(), gameMap));
        gameMap.at(6,4).addActor(new AnonymousTrader());

        world.run();
    }
}
