package com.angrybird;

import com.angrybird.characters.birds.Bird;
import com.angrybird.characters.birds.BlueBird;
import com.angrybird.characters.birds.RedBird;
import com.angrybird.characters.birds.YellowBird;
import com.angrybird.characters.obstacles.*;
import com.angrybird.characters.pigs.Pig;
import com.angrybird.characters.pigs.PigKing;
import com.angrybird.characters.pigs.SimplePig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SavedGamesScreen implements Screen {
    private final Main game; // Reference to the game instance
    private Stage stage;
    private Texture backgroundTexture;

    public SavedGamesScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load and set the background image
        backgroundTexture = new Texture("pause_background.png"); // Replace with your image file
        Image backgroundImage = new Image(new TextureRegionDrawable(backgroundTexture));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        // Create table for button alignment
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Create button style with larger text and black box background
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        BitmapFont font = new BitmapFont(); // Default font
        font.getData().setScale(2.0f); // Make the text larger
        buttonStyle.font = font;

        // Create a black box texture
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        Drawable blackBox = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();

        // Set the black box as the background
        buttonStyle.up = blackBox; // Normal state background
        buttonStyle.down = blackBox; // Pressed state background

        // Create buttons
        TextButton savedGame1 = new TextButton("Saved Game 1", buttonStyle);
        TextButton savedGame2 = new TextButton("Saved Game 2", buttonStyle);
        TextButton savedGame3 = new TextButton("Saved Game 3", buttonStyle);

        // Add click listeners
        savedGame1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Saved Game 1 clicked!");
                loadgame(1);
            }
        });

        savedGame2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Saved Game 2 clicked!");
                loadgame(2);
            }
        });

        savedGame3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Saved Game 3 clicked!");
                loadgame(3);
            }
        });

        // Add buttons to the table with padding
        table.add(savedGame1).padBottom(30).width(300).height(100).row();
        table.add(savedGame2).padBottom(30).width(300).height(100).row();
        table.add(savedGame3).width(300).height(100).row();
    }
    public void loadgame(int l){
        FileHandle file;
        if(l==1){
            file = Gdx.files.local("savegame1.txt");
        }
        else if(l==2){
            file = Gdx.files.local("savegame2.txt");
        }
        else{
            file = Gdx.files.local("savegame3.txt");
        }
        if (file.exists()) {
            String data = file.readString();
            String[] lines = data.split("\n");
            Level newLevel = new Level(game);
            newLevel.levelno = Integer.parseInt(lines[0].split(":")[1].trim());

            if(newLevel.levelno == 1) {
                newLevel.spriteBatch = new SpriteBatch();
                newLevel.slingshotx = 33;
                newLevel.slingshoty = 32;
                newLevel.background = new Texture("level1.jpg");
                newLevel.viewport = new FitViewport(250, 120);
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody; // Now affected by gravity
                bodyDef.position.set(33, 28);
                bodyDef.gravityScale = 1.0f; // Add gravity

                // Create the body
                Body body = newLevel.world.createBody(bodyDef);
                // Add this to constructor after creating the body
                body.setUserData(this); // For collision detection
                // Create the shape
                PolygonShape box = new PolygonShape();
                box.setAsBox(3, 5); // Half-width and half-height

                // Create the fixture
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = box;
                body.createFixture(fixtureDef);
                box.dispose();

                BodyDef bodyDef2 = new BodyDef();
                bodyDef2.type = BodyDef.BodyType.StaticBody; // Now affected by gravity
                bodyDef2.position.set(25, 28);
                bodyDef2.gravityScale = 1.0f; // Add gravity

                // Create the body
                Body body2 = newLevel.world.createBody(bodyDef2);
                // Add this to constructor after creating the body
                body.setUserData(this); // For collision detection
                // Create the shape
                PolygonShape box2 = new PolygonShape();
                box2.setAsBox(2, 250); // Half-width and half-height

                // Create the fixture
                FixtureDef fixtureDef2 = new FixtureDef();
                fixtureDef2.shape = box;
                body2.createFixture(fixtureDef2);
                box2.dispose();

                newLevel.SlingshotSprite.setX(25);
                newLevel.SlingshotSprite.setY(26);
                BodyDef groundBodyDef = new BodyDef();
                groundBodyDef.type = BodyDef.BodyType.StaticBody;
                groundBodyDef.position.set(125, 24); // Center X at 125, Y slightly below base

                // Create the body
                Body groundBody = newLevel.world.createBody(groundBodyDef);

                // Define the shape of the ground
                PolygonShape groundShape = new PolygonShape();
                groundShape.setAsBox(125, 3); // Adjust width and height

                // Attach the shape to the body
                groundBody.createFixture(groundShape, 0.0f);

                // Dispose of the shape to free resources
                groundShape.dispose();
                // Parse birds

                // Parse and load birds, pigs, and obstacles from the save file
                for (int i = 1; i < lines.length; i++) {
                    String line = lines[i].trim();
                    if (line.startsWith("Bird")) {
                        String[] parts = line.split(" ");
                        String birdType = parts[1];
                        float xpos = Float.parseFloat(parts[3].split(",")[0]);
                        float ypos = Float.parseFloat(parts[3].split(",")[1]);
                        float velX = Float.parseFloat(parts[5].split(",")[0]);
                        float velY = Float.parseFloat(parts[5].split(",")[1]);
                        int damage = Integer.parseInt(parts[7]);
                        boolean isLaunched = Boolean.parseBoolean(parts[9]); // Load isLaunched attribute

                        Bird bird = null;
                        if (birdType.equals("RedBird")) {
                            bird = new RedBird(newLevel.world, xpos, ypos);
                        } else if (birdType.equals("YellowBird")) {
                            bird = new YellowBird(newLevel.world, xpos, ypos);
                        } else if (birdType.equals("BlueBird")) {
                            bird = new BlueBird(newLevel.world, xpos, ypos);
                        }
                        if (bird != null) {
                            bird.getBody().setLinearVelocity(new Vector2(velX, velY));  // Set velocity
                            bird.setDamage(damage);  // Set damage
                            bird.setIslaunched(isLaunched); // Set the isLaunched attribute
                            newLevel.birds.add(bird); // Add bird to the new level
                        }
                    } else if (line.startsWith("Pig")) {
                        String[] parts = line.split(" ");
                        String pigType = parts[1];
                        float xpos = Float.parseFloat(parts[3].split(",")[0]);
                        float ypos = Float.parseFloat(parts[3].split(",")[1]);
                        float velX = Float.parseFloat(parts[5].split(",")[0]);
                        float velY = Float.parseFloat(parts[5].split(",")[1]);
                        int health = Integer.parseInt(parts[7]);

                        Pig pig = null;
                        if (pigType.equals("SimplePig")) {
                            pig = new SimplePig(newLevel.world, xpos, ypos);
                        } else if (pigType.equals("PigKing")) {
                            pig = new PigKing(newLevel.world, xpos, ypos);
                        }

                        if (pig != null) {
                            pig.setHealth(health);
                            pig.getBody().setLinearVelocity(new Vector2(velX, velY));  // Set velocity
                            newLevel.pigs.add(pig); // Add pig to the new level
                        }
                    } else if (line.startsWith("Obstacle")) {
                        String[] parts = line.split(" ");
                        String obstacleType = parts[1];
                        float xpos = Float.parseFloat(parts[3].split(",")[0]);
                        float ypos = Float.parseFloat(parts[3].split(",")[1]);
                        float velX = Float.parseFloat(parts[5].split(",")[0]);
                        float velY = Float.parseFloat(parts[5].split(",")[1]);
                        int health = Integer.parseInt(parts[7]);
                        float angle= Float.parseFloat(parts[9]);

                        Obstacle obstacle = null;
                        if (obstacleType.equals("Glass")) {
                            obstacle = new Glass(newLevel.world, xpos, ypos, 5, 20);
                        } else if (obstacleType.equals("Wood")) {
                            obstacle = new Wood(newLevel.world, xpos, ypos, 5, 20);
                        } else if (obstacleType.equals("Woodh")) {
                            obstacle = new Woodh(newLevel.world, xpos, ypos, 20, 5);
                        } else if (obstacleType.equals("Glassh")) {
                            obstacle = new Glassh(newLevel.world, xpos, ypos, 20, 5);
                        } else if (obstacleType.equals("Stone")) {
                            obstacle = new Stone(newLevel.world, xpos, ypos, 5, 20);
                        } else if (obstacleType.equals("Stoneh")) {
                            obstacle = new Stoneh(newLevel.world, xpos, ypos, 20, 5);
                        }

                        if (obstacle != null) {
                            obstacle.setHealth(health);
                            obstacle.getBody().setLinearVelocity(new Vector2(velX, velY)); // Set velocity
                            obstacle.getBody().setTransform(obstacle.getBody().getPosition(), angle); // Set angle (rotation)
                            obstacle.getSprite().setRotation((float) Math.toDegrees(angle)); // Update sprite rotation
                            newLevel.obstacles.add(obstacle); // Add obstacle to the new level
                        }
                    }
                    else{
                        newLevel.score=Integer.parseInt(line);
                    }
                }

                System.out.println("Game loaded successfully!");
            }
            else if(newLevel.levelno==2){
                newLevel.spriteBatch = new SpriteBatch();
                newLevel.slingshotx = 38;
                newLevel.slingshoty = 43;
                newLevel.background = new Texture("level2.jpg");
                newLevel.viewport = new FitViewport(250, 120);
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody; // Now affected by gravity
                bodyDef.position.set(38, 39);
                bodyDef.gravityScale = 1.0f; // Add gravity

                // Create the body
                Body body = newLevel.world.createBody(bodyDef);
                // Add this to constructor after creating the body
                body.setUserData(this); // For collision detection
                // Create the shape
                PolygonShape box = new PolygonShape();
                box.setAsBox(3, 5); // Half-width and half-height

                // Create the fixture
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = box;
                body.createFixture(fixtureDef);
                box.dispose();

                BodyDef bodyDef2 = new BodyDef();
                bodyDef2.type = BodyDef.BodyType.StaticBody; // Now affected by gravity
                bodyDef2.position.set(30, 39);
                bodyDef2.gravityScale = 1.0f; // Add gravity

                // Create the body
                Body body2 = newLevel.world.createBody(bodyDef2);
                // Add this to constructor after creating the body
                body.setUserData(this); // For collision detection
                // Create the shape
                PolygonShape box2 = new PolygonShape();
                box2.setAsBox(2, 250); // Half-width and half-height

                // Create the fixture
                FixtureDef fixtureDef2 = new FixtureDef();
                fixtureDef2.shape = box;
                body2.createFixture(fixtureDef2);
                box2.dispose();

                newLevel.SlingshotSprite.setX(30);
                newLevel.SlingshotSprite.setY(37);
                BodyDef groundBodyDef = new BodyDef();
                groundBodyDef.type = BodyDef.BodyType.StaticBody;
                groundBodyDef.position.set(125, 35); // Center X at 125, Y slightly below base

                // Create the body
                Body groundBody = newLevel.world.createBody(groundBodyDef);

                // Define the shape of the ground
                PolygonShape groundShape = new PolygonShape();
                groundShape.setAsBox(125, 3); // Adjust width and height

                // Attach the shape to the body
                groundBody.createFixture(groundShape, 0.0f);

                // Dispose of the shape to free resources
                groundShape.dispose();
                // Parse birds
                for (int i = 1; i < lines.length; i++) {
                    String line = lines[i].trim();
                    if (line.startsWith("Bird")) {
                        String[] parts = line.split(" ");
                        String birdType = parts[1];
                        float xpos = Float.parseFloat(parts[3].split(",")[0]);
                        float ypos = Float.parseFloat(parts[3].split(",")[1]);
                        float velX = Float.parseFloat(parts[5].split(",")[0]);
                        float velY = Float.parseFloat(parts[5].split(",")[1]);
                        int damage = Integer.parseInt(parts[7]);
                        boolean isLaunched = Boolean.parseBoolean(parts[9]); // Load isLaunched attribute

                        Bird bird = null;
                        if (birdType.equals("RedBird")) {
                            bird = new RedBird(newLevel.world, xpos, ypos);
                        } else if (birdType.equals("YellowBird")) {
                            bird = new YellowBird(newLevel.world, xpos, ypos);
                        } else if (birdType.equals("BlueBird")) {
                            bird = new BlueBird(newLevel.world, xpos, ypos);
                        }
                        if (bird != null) {
                            bird.getBody().setLinearVelocity(new Vector2(velX, velY));  // Set velocity
                            bird.setDamage(damage);  // Set damage
                            bird.setIslaunched(isLaunched); // Set the isLaunched attribute
                            newLevel.birds.add(bird); // Add bird to the new level
                        }
                    } else if (line.startsWith("Pig")) {
                        String[] parts = line.split(" ");
                        String pigType = parts[1];
                        float xpos = Float.parseFloat(parts[3].split(",")[0]);
                        float ypos = Float.parseFloat(parts[3].split(",")[1]);
                        float velX = Float.parseFloat(parts[5].split(",")[0]);
                        float velY = Float.parseFloat(parts[5].split(",")[1]);
                        int health = Integer.parseInt(parts[7]);

                        Pig pig = null;
                        if (pigType.equals("SimplePig")) {
                            pig = new SimplePig(newLevel.world, xpos, ypos);
                        } else if (pigType.equals("PigKing")) {
                            pig = new PigKing(newLevel.world, xpos, ypos);
                        }

                        if (pig != null) {
                            pig.setHealth(health);
                            pig.getBody().setLinearVelocity(new Vector2(velX, velY));  // Set velocity
                            newLevel.pigs.add(pig); // Add pig to the new level
                        }
                    } else if (line.startsWith("Obstacle")) {
                        String[] parts = line.split(" ");
                        String obstacleType = parts[1];
                        float xpos = Float.parseFloat(parts[3].split(",")[0]);
                        float ypos = Float.parseFloat(parts[3].split(",")[1]);
                        float velX = Float.parseFloat(parts[5].split(",")[0]);
                        float velY = Float.parseFloat(parts[5].split(",")[1]);
                        int health = Integer.parseInt(parts[7]);
                        float angle= Float.parseFloat(parts[9]);

                        Obstacle obstacle = null;
                        if (obstacleType.equals("Glass")) {
                            obstacle = new Glass(newLevel.world, xpos, ypos, 5, 20);
                        } else if (obstacleType.equals("Wood")) {
                            obstacle = new Wood(newLevel.world, xpos, ypos, 5, 20);
                        } else if (obstacleType.equals("Woodh")) {
                            obstacle = new Woodh(newLevel.world, xpos, ypos, 20, 5);
                        } else if (obstacleType.equals("Glassh")) {
                            obstacle = new Glassh(newLevel.world, xpos, ypos, 20, 5);
                        } else if (obstacleType.equals("Stone")) {
                            obstacle = new Stone(newLevel.world, xpos, ypos, 5, 20);
                        } else if (obstacleType.equals("Stoneh")) {
                            obstacle = new Stoneh(newLevel.world, xpos, ypos, 20, 5);
                        }

                        if (obstacle != null) {
                            obstacle.setHealth(health);
                            obstacle.getBody().setLinearVelocity(new Vector2(velX, velY)); // Set velocity
                            obstacle.getBody().setTransform(obstacle.getBody().getPosition(), angle); // Set angle (rotation)
                            obstacle.getSprite().setRotation((float) Math.toDegrees(angle)); // Update sprite rotation
                            newLevel.obstacles.add(obstacle); // Add obstacle to the new level
                        }
                    }
                    else{
                        newLevel.score=Integer.parseInt(line);
                    }
                }
            }
            else{
                newLevel.spriteBatch = new SpriteBatch();
                newLevel.slingshotx = 38;
                newLevel.slingshoty = 43;
                newLevel.background = new Texture("level3.jpg");
                newLevel.viewport = new FitViewport(250, 120);
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody; // Now affected by gravity
                bodyDef.position.set(38, 39);
                bodyDef.gravityScale = 1.0f; // Add gravity

                // Create the body
                Body body = newLevel.world.createBody(bodyDef);
                // Add this to constructor after creating the body
                body.setUserData(this); // For collision detection
                // Create the shape
                PolygonShape box = new PolygonShape();
                box.setAsBox(3, 5); // Half-width and half-height

                // Create the fixture
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = box;
                body.createFixture(fixtureDef);
                box.dispose();

                BodyDef bodyDef2 = new BodyDef();
                bodyDef2.type = BodyDef.BodyType.StaticBody; // Now affected by gravity
                bodyDef2.position.set(30, 39);
                bodyDef2.gravityScale = 1.0f; // Add gravity

                // Create the body
                Body body2 = newLevel.world.createBody(bodyDef2);
                // Add this to constructor after creating the body
                body.setUserData(this); // For collision detection
                // Create the shape
                PolygonShape box2 = new PolygonShape();
                box2.setAsBox(2, 250); // Half-width and half-height

                // Create the fixture
                FixtureDef fixtureDef2 = new FixtureDef();
                fixtureDef2.shape = box;
                body2.createFixture(fixtureDef2);
                box2.dispose();

                newLevel.SlingshotSprite.setX(30);
                newLevel.SlingshotSprite.setY(37);
                BodyDef groundBodyDef = new BodyDef();
                groundBodyDef.type = BodyDef.BodyType.StaticBody;
                groundBodyDef.position.set(125, 35); // Center X at 125, Y slightly below base

                // Create the body
                Body groundBody = newLevel.world.createBody(groundBodyDef);

                // Define the shape of the ground
                PolygonShape groundShape = new PolygonShape();
                groundShape.setAsBox(125, 3); // Adjust width and height

                // Attach the shape to the body
                groundBody.createFixture(groundShape, 0.0f);

                // Dispose of the shape to free resources
                groundShape.dispose();
                // Parse birds
                for (int i = 1; i < lines.length; i++) {
                    String line = lines[i].trim();
                    if (line.startsWith("Bird")) {
                        String[] parts = line.split(" ");
                        String birdType = parts[1];
                        float xpos = Float.parseFloat(parts[3].split(",")[0]);
                        float ypos = Float.parseFloat(parts[3].split(",")[1]);
                        float velX = Float.parseFloat(parts[5].split(",")[0]);
                        float velY = Float.parseFloat(parts[5].split(",")[1]);
                        int damage = Integer.parseInt(parts[7]);
                        boolean isLaunched = Boolean.parseBoolean(parts[9]); // Load isLaunched attribute

                        Bird bird = null;
                        if (birdType.equals("RedBird")) {
                            bird = new RedBird(newLevel.world, xpos, ypos);
                        } else if (birdType.equals("YellowBird")) {
                            bird = new YellowBird(newLevel.world, xpos, ypos);
                        } else if (birdType.equals("BlueBird")) {
                            bird = new BlueBird(newLevel.world, xpos, ypos);
                        }
                        if (bird != null) {
                            bird.getBody().setLinearVelocity(new Vector2(velX, velY));  // Set velocity
                            bird.setDamage(damage);  // Set damage
                            bird.setIslaunched(isLaunched); // Set the isLaunched attribute
                            newLevel.birds.add(bird); // Add bird to the new level
                        }
                    } else if (line.startsWith("Pig")) {
                        String[] parts = line.split(" ");
                        String pigType = parts[1];
                        float xpos = Float.parseFloat(parts[3].split(",")[0]);
                        float ypos = Float.parseFloat(parts[3].split(",")[1]);
                        float velX = Float.parseFloat(parts[5].split(",")[0]);
                        float velY = Float.parseFloat(parts[5].split(",")[1]);
                        int health = Integer.parseInt(parts[7]);

                        Pig pig = null;
                        if (pigType.equals("SimplePig")) {
                            pig = new SimplePig(newLevel.world, xpos, ypos);
                        } else if (pigType.equals("PigKing")) {
                            pig = new PigKing(newLevel.world, xpos, ypos);
                        }

                        if (pig != null) {
                            pig.setHealth(health);
                            pig.getBody().setLinearVelocity(new Vector2(velX, velY));  // Set velocity
                            newLevel.pigs.add(pig); // Add pig to the new level
                        }
                    } else if (line.startsWith("Obstacle")) {
                        String[] parts = line.split(" ");
                        String obstacleType = parts[1];
                        float xpos = Float.parseFloat(parts[3].split(",")[0]);
                        float ypos = Float.parseFloat(parts[3].split(",")[1]);
                        float velX = Float.parseFloat(parts[5].split(",")[0]);
                        float velY = Float.parseFloat(parts[5].split(",")[1]);
                        int health = Integer.parseInt(parts[7]);
                        float angle= Float.parseFloat(parts[9]);

                        Obstacle obstacle = null;
                        if (obstacleType.equals("Glass")) {
                            obstacle = new Glass(newLevel.world, xpos, ypos, 5, 20);
                        } else if (obstacleType.equals("Wood")) {
                            obstacle = new Wood(newLevel.world, xpos, ypos, 5, 20);
                        } else if (obstacleType.equals("Woodh")) {
                            obstacle = new Woodh(newLevel.world, xpos, ypos, 20, 5);
                        } else if (obstacleType.equals("Glassh")) {
                            obstacle = new Glassh(newLevel.world, xpos, ypos, 20, 5);
                        } else if (obstacleType.equals("Stone")) {
                            obstacle = new Stone(newLevel.world, xpos, ypos, 5, 20);
                        } else if (obstacleType.equals("Stoneh")) {
                            obstacle = new Stoneh(newLevel.world, xpos, ypos, 20, 5);
                        }

                        if (obstacle != null) {
                            obstacle.setHealth(health);
                            obstacle.getBody().setLinearVelocity(new Vector2(velX, velY)); // Set velocity
                            obstacle.getBody().setTransform(obstacle.getBody().getPosition(), angle); // Set angle (rotation)
                            obstacle.getSprite().setRotation((float) Math.toDegrees(angle)); // Update sprite rotation
                            newLevel.obstacles.add(obstacle); // Add obstacle to the new level
                        }
                    }
                    else{
                        newLevel.score=Integer.parseInt(line);
                    }
                }
            }

            game.setScreen(newLevel);  // Go to the loaded level
        } else {
            System.out.println("No saved game found.");
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
