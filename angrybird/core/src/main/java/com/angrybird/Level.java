package com.angrybird;
import com.angrybird.characters.birds.*;
import com.angrybird.characters.obstacles.*;
import com.angrybird.characters.pigs.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Preferences;
import java.util.ArrayList;
import java.util.Objects;

public class Level implements Screen {
    protected   World world;
    int levelno;
    private BitmapFont font;
    Music collideSound=Gdx.audio.newMusic(Gdx.files.internal("collision.mp3"));
    private Bird selectedBird; // Bird being dragged
    private boolean isDragging = false; // Whether the bird is being dragged
    private Vector2 dragStart = new Vector2(); // Start point of the drag
    private Vector2 dragEnd = new Vector2(); // End point of the drag
    private final float MAX_DRAG_DISTANCE = 50f; // Maximum drag distance for the slingshot
    protected Box2DDebugRenderer debugRenderer;
    private Main game;
    private float inputCooldown = 3f; // Cooldown time in seconds
    private float elapsedTime = 0f; // Tracks time since the level was loaded
    public int score;
    public int highScoreLevel1;
    public int highScoreLevel2;
    public int highScoreLevel3;
    private Preferences prefs;
    //    PauseMenuScreen pausemenuscreen;
    SpriteBatch spriteBatch;
    float slingshotx;
    float slingshoty;
    FitViewport viewport;
    Texture background;
    ArrayList<Bird> birds = new ArrayList<Bird>();
    ArrayList<Pig> pigs = new ArrayList<Pig>();
    ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    ArrayList<Obstacle> horizontal = new ArrayList<Obstacle>();
    Texture SlingshotTexture = new Texture("Slingshot.png");
    Sprite SlingshotSprite = new Sprite(SlingshotTexture);
    private ArrayList<Pig> pigsToRemove = new ArrayList<>();
    private ArrayList<Obstacle> obstaclesToRemove=new ArrayList<>();
    public Stage stage;
    ImageButton pauseButton;
    ImageButton backButton;
    private Skin skin;
    private Texture examboardTexture;
    private Texture resumeTexture;
    private Texture saveTexture;
    private Texture muteTexture;
    private Texture unmuteTexture;
    private boolean isMuted = false;
    private Texture arrowTexture;
    private Sprite arrowSprite;
    private boolean paused = false;
    private BitmapFont font1;
    TextButton winButton;
    TextButton loseButton;
    public Level(Main game) {
        this.game = game;
//        pausemenuscreen = new PauseMenuScreen(game, this);
        this.spriteBatch = new SpriteBatch();
        this.viewport = new FitViewport(250, 120);
        font = new BitmapFont(); // Default LibGDX font
        font.setColor(Color.WHITE); // Set text color
        font.getData().setScale(0.2f); // Optional: Scale down the font size
        stage = new Stage(viewport);
        world = new World(new Vector2(0, -19.6f), true);
        debugRenderer = new Box2DDebugRenderer();
        createWorldBounds();
        // Load textures
//        background = new Texture("background.png");
        examboardTexture = new Texture("examboard.png");
        resumeTexture = new Texture("resume.png");
        saveTexture = new Texture("saveandexit.png");
        muteTexture = new Texture("mute.png");
        unmuteTexture = new Texture("unmute.png");
        score = 0;
        font1 = new BitmapFont();
        prefs = Gdx.app.getPreferences("MyPreferences");
        highScoreLevel1 = prefs.getInteger("highScoreLevel1", 0); // Default to 0 if not set
    highScoreLevel2 = prefs.getInteger("highScoreLevel2", 0); // Default to 0 if not set
    highScoreLevel3 = prefs.getInteger("highScoreLevel3", 0); // Default to 0 if not set
        // Initialize the skin for UI elements
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Set up pause button
        ImageButton.ImageButtonStyle pauseStyle = new ImageButton.ImageButtonStyle();
        pauseStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("pause.png")));
        pauseButton = new ImageButton(pauseStyle);
        pauseButton.setSize(20, 20);
        pauseButton.setPosition(viewport.getWorldWidth() - pauseButton.getWidth() - 10, viewport.getWorldHeight() - pauseButton.getHeight() - 3);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPauseDialog();
                return;
            }
        });

        // Set up back button
        ImageButton.ImageButtonStyle backStyle = new ImageButton.ImageButtonStyle();
        backStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("back.png")));
        backButton = new ImageButton(backStyle);
        backButton.setSize(16, 16);
        backButton.setPosition(10, viewport.getWorldHeight() - backButton.getHeight() - 6);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });
        //setupWinAndLoseButtons();
        // Add buttons to the stage
        stage.addActor(pauseButton);
        stage.addActor(backButton);
        //stage.addActor(winButton);
        //stage.addActor(loseButton);
        arrowTexture = new Texture("arrow.png"); // Ensure this file exists in your assets
        arrowSprite = new Sprite(arrowTexture);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
               GameLogic(contact);
            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
    }

    private void showPauseDialog() {
        paused = true;

        // Create darkening overlay
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.5f);
        pixmap.fill();
        Texture overlayTexture = new Texture(pixmap);
        pixmap.dispose();

        Image overlay = new Image(overlayTexture);
        overlay.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        overlay.setPosition(0, 0);
        overlay.getColor().a = 0f;
        overlay.addAction(Actions.fadeIn(0.3f));

        // Create centered dialog
        final Dialog dialog = new Dialog("", skin);
        dialog.setSize(120, 80); // Adjusted size to fit buttons
        dialog.setPosition(
                (viewport.getWorldWidth() - dialog.getWidth()) / 2,
                (viewport.getWorldHeight() - dialog.getHeight()) / 2
        );
        dialog.setBackground(new TextureRegionDrawable(new TextureRegion(examboardTexture)));

        // Create table with specific sizing
        Table buttonTable = new Table();
        buttonTable.defaults().pad(0.25f); // Add padding between buttons
        buttonTable.center(); // Center align all contents

        // Create uniform sized buttons
        float buttonWidth = 60;
        float buttonHeight = 18;

        ImageButton resumeButton = new ImageButton(new TextureRegionDrawable(resumeTexture));
        ImageButton saveButton = new ImageButton(new TextureRegionDrawable(saveTexture));
        ImageButton muteButton = new ImageButton(
                isMuted ? new TextureRegionDrawable(unmuteTexture) : new TextureRegionDrawable(muteTexture)
        );

        // Set uniform sizes
        resumeButton.setSize(buttonWidth, buttonHeight);
        saveButton.setSize(buttonWidth, buttonHeight);
        muteButton.setSize(buttonWidth, buttonHeight);

        // Add buttons to table with uniform sizing
        buttonTable.add(resumeButton).size(buttonWidth, buttonHeight).row();
        buttonTable.add(saveButton).size(buttonWidth, buttonHeight).row();
        buttonTable.add(muteButton).size(buttonWidth, buttonHeight).row();

        // Center the button table vertically and horizontally in the dialog
        dialog.getContentTable().add(buttonTable).expand().padTop(5).padLeft(5).center();

        // Add listeners
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                overlay.remove();
                dialog.remove();
                resumeGame();
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveGame();
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicManager.toggleMute();
                muteButton.getStyle().imageUp = new TextureRegionDrawable(
                        MusicManager.isMuted ? unmuteTexture : muteTexture
                );
            }
        });

        // Add overlay and dialog to stage
        stage.addActor(overlay);
        stage.addActor(dialog);

        // Make dialog modal
        dialog.setModal(true);
        dialog.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }
    private void setupWinAndLoseButtons() {
        // Initialize the Win button with smaller size
        final Level curLevel = this;
        winButton = new TextButton("Win", skin);
        winButton.setSize(40, 20); // Reduced size for smaller appearance
        winButton.setPosition(viewport.getWorldWidth() - winButton.getWidth() - 10, 10); // Bottom-right corner
        winButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//
                game.setScreen(new WinScreen(game , curLevel));
            }
        });

        // Initialize the Lose button with smaller size
        //loseButton = new TextButton("Lose", skin);
        //loseButton.setSize(40, 20); // Reduced size for smaller appearance
        //loseButton.setPosition(viewport.getWorldWidth() - loseButton.getWidth() - 60, 10); // Bottom-right corner
        /*loseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                 game.setScreen(new LoseScreen(game)); // Uncomment if LoseScreen exists
                game.setScreen(new LoseScreen(game ,curLevel)); // Uncomment if LoseScreen exists
            }
        });*/

        // Add buttons to the stage
        //stage.addActor(winButton);
        //stage.addActor(loseButton);
    }
    private void resumeGame() {
        paused = false;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        if(!MusicManager.isMuted){
            MusicManager.play();
        }
        else{
            MusicManager.pause();
        }

        if (paused) {
            // If the game is paused, render the game screen first
            ScreenUtils.clear(Color.BLACK);

            // Apply the viewport
            viewport.apply();

            // Render all sprites
            spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
            if(!MusicManager.isMuted){
                MusicManager.play();
            }
            spriteBatch.begin();
            // Draw background
            spriteBatch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

            // Draw slingshot
            SlingshotSprite.setSize(15, 15);
            SlingshotSprite.draw(spriteBatch);

            // Draw birds
            for (Bird bird : birds) {
                bird.getSprite().draw(spriteBatch);
            }

            // Draw obstacles
            for (Obstacle obstacle : obstacles) {
                obstacle.getSprite().draw(spriteBatch);
            }

            // Draw pigs
            for (Pig pig : pigs) {
                pig.getSprite().draw(spriteBatch);
            }

            spriteBatch.end();

            // Draw the debug lines (hitboxes)
            debugRenderer.render(world, viewport.getCamera().combined);

            // Draw the stage (UI)
            stage.act(delta);
            stage.draw();
            return;
        }
        // Step the physics world
        world.step(1 / 60f, 6, 2);

        // Clear the screen
        ScreenUtils.clear(Color.BLACK);
        // Apply the viewport
        viewport.apply();

        elapsedTime += delta;
        if(elapsedTime>inputCooldown && !birds.isEmpty()){
            launch();
        }
        trackBirdTimers(delta);
        // Render all sprites
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        // Draw background

        spriteBatch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        for (Pig pig : pigs) {
            Vector2 position = pig.getBody().getPosition(); // Get pig's position
            font.draw(spriteBatch, "" + pig.getHealth(), position.x+5, position.y + 5); // Offset for better visibility
        }

// Draw health for obstacles
        for (Obstacle obstacle : obstacles) {
            Vector2 position = obstacle.getBody().getPosition(); // Get obstacle's position
            font.draw(spriteBatch, "" + obstacle.getHealth(), position.x+5, position.y + 5); // Offset for better visibility
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)  && birds.get(birds.size()-1).isIslaunched()) {
            Bird bird = birds.get(birds.size() - 1); // Example: Launch the first bird
            if(bird instanceof BlueBird){
                bird.getBody().setGravityScale(15);
            }
            if(bird instanceof RedBird){
                for (Fixture fixture : bird.getBody().getFixtureList()) {
                    Shape shape = fixture.getShape();
                    if (shape instanceof CircleShape) {
                        CircleShape circle = (CircleShape) shape;
                        float currentRadius = circle.getRadius();
                        circle.setRadius(6f);  // Double the physics radius
                        break;
                    }
                }
                bird.setXsize(14);
                bird.setYsize(14);
                bird.getSprite().setOriginCenter();
            }
            if(bird instanceof YellowBird){
                bird.getBody().setGravityScale(0);
                bird.getBody().setLinearVelocity(bird.getBody().getLinearVelocity().x,0);
            }
            //bird.getBody().applyLinearImpulse(new Vector2(200, 100), bird.getBody().getWorldCenter(), true);
            //System.out.println("Impulse applied to bird!")
        }

        SlingshotSprite.setSize(15, 15);
        SlingshotSprite.draw(spriteBatch);;
        // Draw obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.getSprite().setSize(obstacle.getWidth(), obstacle.getHeight());
            obstacle.getSprite().draw(spriteBatch);
        }
        for (Obstacle obstacle : obstacles) {
            Sprite sprite = obstacle.getSprite();
            Body body = obstacle.getBody();
            sprite.setPosition(
                    body.getPosition().x - sprite.getWidth() / 2,
                    body.getPosition().y - sprite.getHeight() / 2
            );
            sprite.setRotation((float) Math.toDegrees(body.getAngle())); // Rotate based on physics body angle
            sprite.setOriginCenter();
        }
        // Draw birds and pigs
        for (Bird bird : birds) {
            bird.getSprite().setSize(bird.getXsize(), bird.getYsize());
            bird.getSprite().draw(spriteBatch);
        }
        for (Bird bird : birds) {
            Sprite sprite = bird.getSprite();
            Body body = bird.getBody();
            sprite.setPosition(
                    body.getPosition().x - sprite.getWidth() / 2,
                    body.getPosition().y - sprite.getHeight() / 2
            );
            sprite.setRotation((float) Math.toDegrees(body.getAngle()/2)); // Rotate based on physics body angle
            sprite.setOriginCenter();
        }
        for (Pig pig : pigs) {
            pig.getSprite().setSize(pig.getXsize(), pig.getYsize());
            pig.getSprite().draw(spriteBatch);
        }
        for (Pig pig : pigs) {
            Sprite sprite = pig.getSprite();
            Body body = pig.getBody();
            sprite.setPosition(
                    body.getPosition().x - sprite.getWidth() / 2,
                    body.getPosition().y - sprite.getHeight() / 2
            );
            sprite.setRotation((float) Math.toDegrees(body.getAngle()/2)); // Rotate based on physics body angle
            sprite.setOriginCenter();
        }
        if (isDragging && selectedBird != null) {
            drawLaunchArrow();
        }
        if (!pigsToRemove.isEmpty()) {
            for (Pig pig : pigsToRemove) {
                world.destroyBody(pig.getBody());
                pigs.remove(pig);
            }
            pigsToRemove.clear(); // Clear the temporary list
        }
        if (!obstaclesToRemove.isEmpty()) {
            for (Obstacle obstacle : obstaclesToRemove) {
                world.destroyBody(obstacle.getBody());
                pigs.remove(obstacle);
            }
            obstaclesToRemove.clear(); // Clear the temporary list
        }
        if(birds.isEmpty() &&!pigs.isEmpty()){
            updateHighScore();
            game.setScreen(new LoseScreen(game,this));
        }
        else if(!birds.isEmpty() && pigs.isEmpty()){
            updateHighScore();
            game.setScreen(new WinScreen(game,this));
        }

        font1.setColor(Color.WHITE);
        font1.getData().setScale(0.4f); // Adjust the scale as needed
        String scoreText = "Score: " + score;
        String highScoreText = "High Score: ";
        if (this.levelno==1) {
            highScoreText += highScoreLevel1;
        } else if (this.levelno==2) {
            highScoreText += highScoreLevel2;
        } else if (this.levelno==3) {
            highScoreText += highScoreLevel3;
        }
        float x = 60; // X position of the score
        float y = viewport.getWorldHeight() - 10; // Y position of the score
        font1.draw(spriteBatch, scoreText, x, y);
        font1.draw(spriteBatch, highScoreText, x, y - 10); // Adjust Y position for high score
        spriteBatch.end();

        // Draw the debug lines (hitboxes)
        debugRenderer.render(world, viewport.getCamera().combined);
        // Draw UI
        stage.act(delta);
        stage.draw();
    }
    private void saveGame() {
        // Open a file to write the data
        FileHandle file;
        if(this.levelno==1) {
            file=Gdx.files.local("savegame1.txt");
        }
        else if(this.levelno==2){
            file=Gdx.files.local("savegame2.txt");
        }
        else{
            file=Gdx.files.local("savegame3.txt");
        }
        StringBuilder sb = new StringBuilder();

        // Save the level number
        sb.append("Level: ").append(this.levelno).append("\n");

        // Save birds
        for (Bird bird : birds) {
            sb.append("Bird: ").append(bird.getClass().getSimpleName())
                    .append(" Position: ").append(bird.getBody().getPosition().x).append(",").append(bird.getBody().getPosition().y)
                    .append(" Velocity: ").append(bird.getBody().getLinearVelocity().x).append(",").append(bird.getBody().getLinearVelocity().y)
                    .append(" Damage: ").append(bird.getDamage())
                    .append(" Launched: ").append(bird.isIslaunched())  // Save whether the bird has been launched
                    .append("\n");
        }

        // Save pigs
        for (Pig pig : pigs) {
            sb.append("Pig: ").append(pig.getClass().getSimpleName())
                    .append(" Position: ").append(pig.getBody().getPosition().x).append(",").append(pig.getBody().getPosition().y)
                    .append(" Velocity: ").append(pig.getBody().getLinearVelocity().x).append(",").append(pig.getBody().getLinearVelocity().y)
                    .append(" Health: ").append(pig.getHealth())
                    .append("\n");
        }

        // Save obstacles
        for (Obstacle obstacle : obstacles) {
            sb.append("Obstacle: ").append(obstacle.getClass().getSimpleName())
                    .append(" Position: ").append(obstacle.getBody().getPosition().x).append(",").append(obstacle.getBody().getPosition().y)
                    .append(" Velocity: ").append(obstacle.getBody().getLinearVelocity().x).append(",").append(obstacle.getBody().getLinearVelocity().y)
                    .append(" Health: ").append(obstacle.getHealth())
                    .append(" Rotation: ").append(obstacle.getBody().getAngle())
                    .append("\n");
        }
        sb.append(score);
        // Write the state to the file
        file.writeString(sb.toString(), false);
        System.out.println("Game saved!");
    }

    private void updateHighScore() {
        if (this instanceof Level1) {
            if (score > highScoreLevel1) {
                highScoreLevel1 = score;
                prefs.putInteger("highScoreLevel1", highScoreLevel1);
                prefs.flush(); // Save the updated high score
            }
        } else if (this instanceof Level2) {
            if (score > highScoreLevel2) {
                highScoreLevel2 = score;
                prefs.putInteger("highScoreLevel2", highScoreLevel2);
                prefs.flush();
            }
        } else if (this instanceof Level3) {
            if (score > highScoreLevel3) {
                highScoreLevel3 = score;
                prefs.putInteger("highScoreLevel3", highScoreLevel3);
                prefs.flush();
            }
        }
    }
    private void trackBirdTimers(float delta) {
        ArrayList<Bird> toRemove = new ArrayList<>();

        for (Bird bird : birds) {
            if (bird.isIslaunched()) {
                // Update the bird's launch timer
                bird.setLaunchTime(bird.getLaunchTime() + delta);

                // Check if the bird should be removed
                if (bird.getLaunchTime() >= 15f) {
                    // Mark the bird for removal
                    toRemove.add(bird);
                }
            }
        }

        // Remove expired birds and shift remaining birds
        for (Bird bird : toRemove) {
            // Remove bird's body from the physics world
            world.destroyBody(bird.getBody());

            // Remove bird from the list
            birds.remove(bird);

            // Shift remaining birds
            shiftBird();
        }
    }

    private void shiftBird(){
        if (birds.isEmpty()) return;

        // Move remaining birds forward
        /*for (int i = 1; i < birds.size(); i++) {
            Bird currentBird = birds.get(i);
            Bird previousBird = birds.get(i - 1);

            // Move the current bird to the previous bird's position
            Vector2 previousPosition = previousBird.getBody().getPosition();
            currentBird.getBody().setTransform(previousPosition, 0);
        }*/

        // Set the last bird in the list to the slingshot position
        Bird lastBird = birds.get(birds.size() - 1);
        lastBird.getBody().setTransform(new Vector2(slingshotx, slingshoty), 0); // Replace with slingshot position
        lastBird.setIslaunched(false); // Make it ready for launching
        lastBird.setLaunchTime(-1f); // Reset its timer
    }

    private void launch() {
        if (elapsedTime <= inputCooldown) return;
        if (Gdx.input.isTouched()) {
            Vector2 pointer = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            selectedBird = birds.get(birds.size() - 1);
            float x=selectedBird.getXpos();
            float y=selectedBird.getYpos();
            if (!isDragging && !birds.isEmpty()) {
                // Check if the last bird is clicked

                if (selectedBird.getSprite().getBoundingRectangle().contains(pointer.x, pointer.y)) {
                    isDragging = true;
                    dragStart.set(selectedBird.getBody().getPosition()); // Bird's initial position
                }
            }

            if (isDragging) {
                // Update the drag position
                dragEnd.set(pointer);

                // Constrain drag distance
                float dragDistance = dragStart.dst(dragEnd);
                if (dragDistance > MAX_DRAG_DISTANCE) {
                    dragEnd.set(dragStart.cpy().lerp(dragEnd, MAX_DRAG_DISTANCE / dragDistance));
                }

                // Draw a visual line to represent slingshot tension (optional for future)
            }
        } else if (isDragging && !selectedBird.isIslaunched()) {
            // On release, launch the bird
            Vector2 launchForce = dragStart.cpy().sub(dragEnd).scl(100); // Scale force
            selectedBird.setIslaunched(true);
            selectedBird.setLaunchTime(0f);
            selectedBird.getBody().applyLinearImpulse(launchForce, selectedBird.getBody().getWorldCenter(), true);

            // Remove launched bird from the list
            //birds.remove(selectedBird);

            // Reset dragging state
            isDragging = false;
            selectedBird = null;
        }
    }
    private void drawLaunchArrow() {
        if (dragStart.epsilonEquals(dragEnd, 0.1f)) return;

        // Calculate the angle and length between dragStart and dragEnd
        float dx = dragStart.x - dragEnd.x;
        float dy = dragStart.y - dragEnd.y;
        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(dy, dx);
        float length = dragStart.dst(dragEnd);

        // Set the arrow sprite properties
        arrowSprite.setPosition(dragStart.x, dragStart.y-3);
        arrowSprite.setSize(length, 5f); // Adjust the height for arrow thickness
        arrowSprite.setOrigin(0, selectedBird.getSprite().getHeight() / 2);
        arrowSprite.setRotation(angle);

        // Draw the arrow sprite
        arrowSprite.draw(spriteBatch);
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }
    private void checkBlocksAbove(float x, float y) {
        // Check each obstacle to see if it's above the destroyed block
        for (Obstacle obstacle : obstacles) {
            // Get position of current obstacle
            float obstacleX = obstacle.getBody().getPosition().x;
            float obstacleY = obstacle.getBody().getPosition().y;

            // Check if obstacle is above the destroyed block (within a small horizontal range)
            float horizontalRange = 2f; // Adjust this value based on your block sizes
            if (Math.abs(obstacleX - x) < horizontalRange && obstacleY > y) {
                // Deal one hit of damage
                obstacle.setHealth(obstacle.getHealth() - 5);
                score += 20;

                // If block is destroyed, trigger cascade
                if (obstacle.getHealth() <= 0) {
                    float destroyedX = obstacle.getBody().getPosition().x;
                    float destroyedY = obstacle.getBody().getPosition().y;

                    obstaclesToRemove.add(obstacle);
                    obstacles.remove(obstacle);
                    score += 50;

                    // Recursively check blocks above this one
                    checkBlocksAbove(destroyedX, destroyedY);
                    break; // Break to avoid ConcurrentModificationException
                }
            }
        }
    }
    private void GameLogic(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userDataA = fixtureA.getBody().getUserData();
        Object userDataB = fixtureB.getBody().getUserData();

        // Bird collides with Pig
        if (userDataA instanceof Pig && userDataB instanceof Bird) {
            if(userDataB instanceof YellowBird){
                YellowBird yellowBird=(YellowBird) userDataB;
                yellowBird.getBody().setGravityScale(1);
            }
            Pig pig = (Pig) userDataA;
            pig.setHealth(pig.getHealth() - 20);
            score+=20;

            if (pig.getHealth() <= 0) {
                collideSound.play();
                pigsToRemove.add(pig); // Add to removal list
                score+=100;
            }
        } else if (userDataB instanceof Pig && userDataA instanceof Bird) {
            if(userDataA instanceof YellowBird){
                YellowBird yellowBird=(YellowBird) userDataA;
                yellowBird.getBody().setGravityScale(1);
            }
            Pig pig = (Pig) userDataB;
            pig.setHealth(pig.getHealth() - 20); // Reduce pig's health
            score+=20;

            if (pig.getHealth() <= 0) {
                collideSound.play();
                pigsToRemove.add(pig);
                score+=100;
            }
        }

        if (userDataA instanceof Obstacle && userDataB instanceof Bird) {
            if(userDataB instanceof YellowBird){
                YellowBird yellowBird=(YellowBird) userDataB;
                yellowBird.getBody().setGravityScale(1);
            }
            Obstacle obstacle = (Obstacle) userDataA;
            obstacle.setHealth(obstacle.getHealth() - 20); // Reduce pig's health
            score+=20;
            if (obstacle.getHealth() <= 0) {
                float x = obstacle.getBody().getPosition().x;
                float y = obstacle.getBody().getPosition().y;

                obstaclesToRemove.add(obstacle);
                obstacles.remove(obstacle);
                score += 50;

                // Check for cascade destruction
                checkBlocksAbove(x, y);
            }
        } else if (userDataB instanceof Obstacle && userDataA instanceof Bird) {
            if(userDataA instanceof YellowBird){
                YellowBird yellowBird=(YellowBird) userDataB;
                yellowBird.getBody().setGravityScale(1);
            }
            Obstacle obstacle = (Obstacle) userDataB;
            obstacle.setHealth(obstacle.getHealth() - 20);
             // Reduce pig's health
            score+=20;
            if (obstacle.getHealth() <= 0) {
                float x = obstacle.getBody().getPosition().x;
                float y = obstacle.getBody().getPosition().y;

                obstaclesToRemove.add(obstacle);
                obstacles.remove(obstacle);
                score += 50;

                // Check for cascade destruction
                checkBlocksAbove(x, y);

            }

        }
        if (userDataA instanceof Pig && userDataB instanceof Obstacle) {
            Pig pig = (Pig) userDataA;
            float impactForce = calculateImpactForce(contact);

            if (impactForce > 5) { // Threshold value
                score+= (int) impactForce;
                pig.setHealth(pig.getHealth() - (int) impactForce);
                if(pig.getHealth() <= 0){
                    collideSound.play();
                    pigsToRemove.add(pig);
                    pigs.remove(pig);
                    score+=100;
                }
            }
        } else if (userDataB instanceof Pig && userDataA instanceof Obstacle) {
            Pig pig = (Pig) userDataB;
            float impactForce = calculateImpactForce(contact);
            if (impactForce > 5) { // Threshold value
                score+=impactForce;
                pig.setHealth(pig.getHealth() - (int) impactForce);
                if(pig.getHealth() <= 0){

                    collideSound.play();
                    pigsToRemove.add(pig);
                    pigs.remove(pig);
                    score+=100;
                }
            }
        }
        if (userDataA instanceof Pig && fixtureB.getBody().getType() == BodyDef.BodyType.StaticBody) {
            Pig pig = (Pig) userDataA;
            if(pig.getYpos()-pig.getSprite().getY()>10 &&!pig.isFalldamage()){
                pig.setHealth( (int) (pig.getHealth()-((pig.getYpos()-pig.getSprite().getY())*0.5f)));
                pig.setFalldamage(true);
                score+= (pig.getYpos()-pig.getSprite().getY())*0.5f;
                if(pig.getHealth() <= 0){
                    collideSound.play();
                    pigsToRemove.add(pig);
                    pigs.remove(pig);

                    score+=100;
                }
            }
        } else if (userDataB instanceof Pig && fixtureA.getBody().getType() == BodyDef.BodyType.StaticBody) {
            Pig pig = (Pig) userDataB;
            if(pig.getYpos()-pig.getSprite().getY()>10 &&!pig.isFalldamage()){
                pig.setHealth((int) (pig.getHealth()-((pig.getYpos()-pig.getSprite().getY())*0.5f)));
                pig.setFalldamage(true);
                score+= (pig.getYpos()-pig.getSprite().getY())*0.5f;
                if(pig.getHealth() <= 0){
                    collideSound.play();
                    pigsToRemove.add(pig);
                    pigs.remove(pig);
                    score+=100;
                }
            }
        }
        if((userDataB instanceof YellowBird && fixtureA.getBody().getType() == BodyDef.BodyType.StaticBody)){
            YellowBird yellowBird=(YellowBird) userDataB;
            yellowBird.getBody().setGravityScale(1);
        }
        else if (userDataA instanceof YellowBird && fixtureB.getBody().getType() == BodyDef.BodyType.StaticBody) {
            YellowBird yellowBird=(YellowBird) userDataA;
            yellowBird.getBody().setGravityScale(1);
        }
        // Example: Add more collision logic here

    }
    private float calculateImpactForce(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        Vector2 relativeVelocity = bodyA.getLinearVelocity().sub(bodyB.getLinearVelocity());
        float relativeSpeed = relativeVelocity.len();

        float massA = bodyA.getMass();
        float massB = bodyB.getMass();
        float effectiveMass = (massA * massB) / (massA + massB);

        float impactForce = relativeSpeed * effectiveMass * 0.05f;

        return impactForce;
    }
    private void createWorldBounds() {
        // Left boundary
        BodyDef leftWallDef = new BodyDef();
        leftWallDef.type = BodyDef.BodyType.StaticBody;
        leftWallDef.position.set(0, viewport.getWorldHeight() / 2);
        Body leftWall = world.createBody(leftWallDef);
        EdgeShape leftEdge = new EdgeShape();
        leftEdge.set(0, -viewport.getWorldHeight() / 2, 0, viewport.getWorldHeight() / 2);
        leftWall.createFixture(leftEdge, 0);
        leftEdge.dispose();

        // Right boundary
        BodyDef rightWallDef = new BodyDef();
        rightWallDef.type = BodyDef.BodyType.StaticBody;
        rightWallDef.position.set(viewport.getWorldWidth(), viewport.getWorldHeight() / 2);
        Body rightWall = world.createBody(rightWallDef);
        EdgeShape rightEdge = new EdgeShape();
        rightEdge.set(0, -viewport.getWorldHeight() / 2, 0, viewport.getWorldHeight() / 2);
        rightWall.createFixture(rightEdge, 0);
        rightEdge.dispose();

        // Top boundary
        BodyDef topWallDef = new BodyDef();
        topWallDef.type = BodyDef.BodyType.StaticBody;
        topWallDef.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight());
        Body topWall = world.createBody(topWallDef);
        EdgeShape topEdge = new EdgeShape();
        topEdge.set(-viewport.getWorldWidth() / 2, 0, viewport.getWorldWidth() / 2, 0);
        topWall.createFixture(topEdge, 0);
        topEdge.dispose();
    }
    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        spriteBatch.dispose();
        stage.dispose();
        background.dispose();
        SlingshotTexture.dispose();
        examboardTexture.dispose();
        resumeTexture.dispose();
        saveTexture.dispose();
        muteTexture.dispose();
        unmuteTexture.dispose();
        font.dispose();
        font1.dispose();
    }
}
