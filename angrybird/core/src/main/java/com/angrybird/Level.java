package com.angrybird;
import com.angrybird.characters.birds.*;
import com.angrybird.characters.obstacles.*;
import com.angrybird.characters.pigs.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

import java.util.ArrayList;
import java.util.Objects;

public class Level implements Screen {
    protected   World world;
    private Bird selectedBird; // Bird being dragged
    private boolean isDragging = false; // Whether the bird is being dragged
    private Vector2 dragStart = new Vector2(); // Start point of the drag
    private Vector2 dragEnd = new Vector2(); // End point of the drag
    private final float MAX_DRAG_DISTANCE = 50f; // Maximum drag distance for the slingshot
    protected Box2DDebugRenderer debugRenderer;
    private Main game;
    private float inputCooldown = 1.5f; // Cooldown time in seconds
    private float elapsedTime = 0f; // Tracks time since the level was loaded
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
    TextButton winButton;
    TextButton loseButton;
    public Level(Main game) {
        this.game = game;
//        pausemenuscreen = new PauseMenuScreen(game, this);
        this.spriteBatch = new SpriteBatch();
        this.viewport = new FitViewport(250, 120);
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
        setupWinAndLoseButtons();
        // Add buttons to the stage
        stage.addActor(pauseButton);
        stage.addActor(backButton);
        stage.addActor(winButton);
        stage.addActor(loseButton);
        arrowTexture = new Texture("arrow.png"); // Ensure this file exists in your assets
        arrowSprite = new Sprite(arrowTexture);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                handleCollision(contact);
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
        // Create dialog
        Dialog dialog = new Dialog("", skin);
        dialog.setSize(125, 60);
        dialog.setPosition(47, 18);

        // Set background image
        dialog.setBackground(new TextureRegionDrawable(new TextureRegion(examboardTexture)) {
            @Override
            public void draw(Batch batch, float x, float y, float width, float height) {
                batch.draw(examboardTexture, 47, 18, 155, 90);
            }
        });

        dialog.getColor().a = 0f;
        dialog.addAction(Actions.fadeIn(0.5f));

        Table overlay = new Table();
        overlay.setFillParent(true);
        overlay.setBackground(new TextureRegionDrawable(new Texture(1, 1, Pixmap.Format.RGB888))
                .tint(new Color(0, 0, 0, 0.5f)));
        stage.addActor(overlay);
        overlay.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));

        // Create and position buttons directly on stage
        ImageButton resumeButton = new ImageButton(new TextureRegionDrawable(resumeTexture));
        ImageButton saveButton = new ImageButton(new TextureRegionDrawable(saveTexture));
        ImageButton muteButton = new ImageButton(
                isMuted ? new TextureRegionDrawable(unmuteTexture) : new TextureRegionDrawable(muteTexture)
        );

        // Set fixed sizes for buttons
        resumeButton.setSize(50, 25);
        saveButton.setSize(50, 25);
        muteButton.setSize(50, 25);

        // Position buttons relative to examboard position
        float startX = 105;  // Center of examboard
        float startY = 70;   // Top of the button area
        float spacing = 20;  // Space between buttons

        resumeButton.setPosition(startX, startY);
        saveButton.setPosition(startX, startY - spacing);
        muteButton.setPosition(startX, startY - spacing * 2);

        // Add listeners
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeButton.remove();
                saveButton.remove();
                muteButton.remove();
                overlay.remove();
                dialog.hide();
                resumeGame();
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isMuted = !isMuted;
                muteButton.getStyle().imageUp = new TextureRegionDrawable(
                        isMuted ? unmuteTexture : muteTexture
                );
            }
        });

        // Add buttons directly to stage
        stage.addActor(dialog);
        stage.addActor(resumeButton);
        stage.addActor(saveButton);
        stage.addActor(muteButton);

        dialog.show(stage);
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
        loseButton = new TextButton("Lose", skin);
        loseButton.setSize(40, 20); // Reduced size for smaller appearance
        loseButton.setPosition(viewport.getWorldWidth() - loseButton.getWidth() - 60, 10); // Bottom-right corner
        loseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                 game.setScreen(new LoseScreen(game)); // Uncomment if LoseScreen exists
                game.setScreen(new LoseScreen(game ,curLevel)); // Uncomment if LoseScreen exists
            }
        });

        // Add buttons to the stage
        stage.addActor(winButton);
        stage.addActor(loseButton);
    }
    private void resumeGame() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        
    }

    @Override
    public void render(float delta) {
        // Step the physics world
        world.step(1 / 60f, 6, 2);

        // Clear the screen
        ScreenUtils.clear(Color.BLACK);
        // Apply the viewport
        viewport.apply();
        elapsedTime += delta;
        if(elapsedTime>inputCooldown && !birds.isEmpty()){
            handleInput();
        }
        trackBirdTimers(delta);
        // Render all sprites
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        // Draw background
        spriteBatch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Bird bird = birds.get(birds.size() - 1); // Example: Launch the first bird
            bird.getBody().applyLinearImpulse(new Vector2(200, 100), bird.getBody().getWorldCenter(), true);
            System.out.println("Impulse applied to bird!");
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
        spriteBatch.end();

        // Draw the debug lines (hitboxes)
        debugRenderer.render(world, viewport.getCamera().combined);

        // Draw UI
        stage.act(delta);
        stage.draw();
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

    private void handleInput() {
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
            Vector2 launchForce = dragStart.cpy().sub(dragEnd).scl(20); // Scale force
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

    private void handleCollision(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userDataA = fixtureA.getBody().getUserData();
        Object userDataB = fixtureB.getBody().getUserData();

        // Bird collides with Pig
        if (userDataA instanceof Pig && userDataB instanceof Bird) {
            Pig pig = (Pig) userDataA;
            pig.setHealth(pig.getHealth() - 20); // Reduce pig's health
        } else if (userDataB instanceof Pig && userDataA instanceof Bird) {
            Pig pig = (Pig) userDataB;
            pig.setHealth(pig.getHealth() - 20); // Reduce pig's health
        }

        // Example: Add more collision logic here
        if (userDataA instanceof Obstacle || userDataB instanceof Obstacle) {
            // Handle obstacle collisions
        }
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
    }
}


