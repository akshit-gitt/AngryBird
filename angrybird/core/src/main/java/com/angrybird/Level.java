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
    private Bird selectedBird;
    private boolean isDragging = false;
    private Vector2 dragStart = new Vector2();
    private Vector2 dragEnd = new Vector2();
    private final float MAX_DRAG_DISTANCE = 50f;
    protected Box2DDebugRenderer debugRenderer;
    private Main game;
    private float inputCooldown = 3f;
    private float elapsedTime = 0f;
    public int score;
    public int highScoreLevel1;
    public int highScoreLevel2;
    public int highScoreLevel3;
    private Preferences prefs;
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
   // TextButton winButton;
    //TextButton loseButton;
    public Level(Main game) {
        this.game = game;
        this.spriteBatch = new SpriteBatch();
        this.viewport = new FitViewport(250, 120);
        font = new BitmapFont(); // Default LibGDX font
        font.setColor(Color.WHITE); // Set text color
        font.getData().setScale(0.2f); // Optional: Scale down the font size
        stage = new Stage(viewport);
        world = new World(new Vector2(0, -19.6f), true);
        //debugRenderer = new Box2DDebugRenderer();
        createWorldBounds();
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
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

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

        final Dialog dialog = new Dialog("", skin);
        dialog.setSize(120, 80); // Adjusted size to fit buttons
        dialog.setPosition(
                (viewport.getWorldWidth() - dialog.getWidth()) / 2,
                (viewport.getWorldHeight() - dialog.getHeight()) / 2
        );
        dialog.setBackground(new TextureRegionDrawable(new TextureRegion(examboardTexture)));

        Table buttonTable = new Table();
        buttonTable.defaults().pad(0.25f);
        buttonTable.center();

        float buttonWidth = 60;
        float buttonHeight = 18;

        ImageButton resumeButton = new ImageButton(new TextureRegionDrawable(resumeTexture));
        ImageButton saveButton = new ImageButton(new TextureRegionDrawable(saveTexture));
        ImageButton muteButton = new ImageButton(
                isMuted ? new TextureRegionDrawable(unmuteTexture) : new TextureRegionDrawable(muteTexture)
        );

        resumeButton.setSize(buttonWidth, buttonHeight);
        saveButton.setSize(buttonWidth, buttonHeight);
        muteButton.setSize(buttonWidth, buttonHeight);

        buttonTable.add(resumeButton).size(buttonWidth, buttonHeight).row();
        buttonTable.add(saveButton).size(buttonWidth, buttonHeight).row();
        buttonTable.add(muteButton).size(buttonWidth, buttonHeight).row();

        dialog.getContentTable().add(buttonTable).expand().padTop(5).padLeft(5).center();

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

        stage.addActor(overlay);
        stage.addActor(dialog);

        dialog.setModal(true);
        dialog.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }
    /*private void setupWinAndLoseButtons() {
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
        //stage.addActor(loseButton);*/

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
            ScreenUtils.clear(Color.BLACK);
            viewport.apply();
            spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
            if(!MusicManager.isMuted){
                MusicManager.play();
            }
            spriteBatch.begin();
            spriteBatch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
            SlingshotSprite.setSize(15, 15);
            SlingshotSprite.draw(spriteBatch);

            for (Bird bird : birds) {
                bird.getSprite().draw(spriteBatch);
            }

            for (Obstacle obstacle : obstacles) {
                obstacle.getSprite().draw(spriteBatch);
            }

            for (Pig pig : pigs) {
                pig.getSprite().draw(spriteBatch);
            }

            spriteBatch.end();

            //debugRenderer.render(world, viewport.getCamera().combined);

            stage.act(delta);
            stage.draw();
            return;
        }
        world.step(1 / 60f, 6, 2);

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        elapsedTime += delta;
        if(elapsedTime>inputCooldown && !birds.isEmpty()){
            launch();
        }
        trackBirdTimers(delta);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        // Draw background

        spriteBatch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        /*for (Pig pig : pigs) {
            Vector2 position = pig.getBody().getPosition(); // Get pig's position
            font.draw(spriteBatch, "" + pig.getHealth(), position.x+5, position.y + 5); // Offset for better visibility
        }*/

/*// Draw health for obstacles
        for (Obstacle obstacle : obstacles) {
            Vector2 position = obstacle.getBody().getPosition(); // Get obstacle's position
            font.draw(spriteBatch, "" + obstacle.getHealth(), position.x+5, position.y + 5); // Offset for better visibility
        }*/

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
        }

        SlingshotSprite.setSize(15, 15);
        SlingshotSprite.draw(spriteBatch);;

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
            sprite.setRotation((float) Math.toDegrees(body.getAngle()/2));
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
            sprite.setRotation((float) Math.toDegrees(body.getAngle()/2));
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
            pigsToRemove.clear();
        }
        if (!obstaclesToRemove.isEmpty()) {
            for (Obstacle obstacle : obstaclesToRemove) {
                world.destroyBody(obstacle.getBody());
                obstacles.remove(obstacle);
            }
            obstaclesToRemove.clear();
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
        font1.getData().setScale(0.4f);
        String scoreText = "Score: " + score;
        String highScoreText = "High Score: ";
        if (this.levelno==1) {
            highScoreText += highScoreLevel1;
        } else if (this.levelno==2) {
            highScoreText += highScoreLevel2;
        } else if (this.levelno==3) {
            highScoreText += highScoreLevel3;
        }
        float x = 60;
        float y = viewport.getWorldHeight() - 10;
        font1.draw(spriteBatch, scoreText, x, y);
        font1.draw(spriteBatch, highScoreText, x, y - 10);
        spriteBatch.end();

        //debugRenderer.render(world, viewport.getCamera().combined);

        stage.act(delta);
        stage.draw();
    }
    private void saveGame() {
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
        sb.append("Level: ").append(this.levelno).append("\n");

        for (Bird bird : birds) {
            sb.append("Bird: ").append(bird.getClass().getSimpleName())
                    .append(" Position: ").append(bird.getBody().getPosition().x).append(",").append(bird.getBody().getPosition().y)
                    .append(" Velocity: ").append(bird.getBody().getLinearVelocity().x).append(",").append(bird.getBody().getLinearVelocity().y)
                    .append(" Damage: ").append(bird.getDamage())
                    .append(" Launched: ").append(bird.isIslaunched())  // Save whether the bird has been launched
                    .append("\n");
        }

        for (Pig pig : pigs) {
            sb.append("Pig: ").append(pig.getClass().getSimpleName())
                    .append(" Position: ").append(pig.getBody().getPosition().x).append(",").append(pig.getBody().getPosition().y)
                    .append(" Velocity: ").append(pig.getBody().getLinearVelocity().x).append(",").append(pig.getBody().getLinearVelocity().y)
                    .append(" Health: ").append(pig.getHealth())
                    .append("\n");
        }

        for (Obstacle obstacle : obstacles) {
            sb.append("Obstacle: ").append(obstacle.getClass().getSimpleName())
                    .append(" Position: ").append(obstacle.getBody().getPosition().x).append(",").append(obstacle.getBody().getPosition().y)
                    .append(" Velocity: ").append(obstacle.getBody().getLinearVelocity().x).append(",").append(obstacle.getBody().getLinearVelocity().y)
                    .append(" Health: ").append(obstacle.getHealth())
                    .append(" Rotation: ").append(obstacle.getBody().getAngle())
                    .append("\n");
        }
        sb.append(score);
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
                bird.setLaunchTime(bird.getLaunchTime() + delta);

                if (bird.getLaunchTime() >= 15f) {
                    toRemove.add(bird);
                }
            }
        }

        // Remove expired birds and shift remaining birds
        for (Bird bird : toRemove) {
            world.destroyBody(bird.getBody());
            birds.remove(bird);
            shiftBird();
        }
    }

    private void shiftBird(){
        if (birds.isEmpty()) return;
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
                dragEnd.set(pointer);

                // Constrain drag distance
                float dragDistance = dragStart.dst(dragEnd);
                if (dragDistance > MAX_DRAG_DISTANCE) {
                    dragEnd.set(dragStart.cpy().lerp(dragEnd, MAX_DRAG_DISTANCE / dragDistance));
                }
            }
        } else if (isDragging && !selectedBird.isIslaunched()) {
            Vector2 launchForce = dragStart.cpy().sub(dragEnd).scl(100); // Scale force
            selectedBird.setIslaunched(true);
            selectedBird.setLaunchTime(0f);
            selectedBird.getBody().applyLinearImpulse(launchForce, selectedBird.getBody().getWorldCenter(), true);
            isDragging = false;
            selectedBird = null;
        }
    }
    private void drawLaunchArrow() {
        if (dragStart.epsilonEquals(dragEnd, 0.1f)) return;

        float dx = dragStart.x - dragEnd.x;
        float dy = dragStart.y - dragEnd.y;
        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(dy, dx);
        float length = dragStart.dst(dragEnd);

        arrowSprite.setPosition(dragStart.x, dragStart.y-3);
        arrowSprite.setSize(length, 5f); // Adjust the height for arrow thickness
        arrowSprite.setOrigin(0, selectedBird.getSprite().getHeight() / 2);
        arrowSprite.setRotation(angle);
        arrowSprite.draw(spriteBatch);
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }
    private void checkBlocksAbove(float x, float y) {
        for (Obstacle obstacle : obstacles) {
            float obstacleX = obstacle.getBody().getPosition().x;
            float obstacleY = obstacle.getBody().getPosition().y;
            float horizontalRange = 2f;
            if (Math.abs(obstacleX - x) < horizontalRange && obstacleY > y) {
                obstacle.setHealth(obstacle.getHealth() - 5);
                score += 20;
                if (obstacle.getHealth() <= 0) {
                    float destroyedX = obstacle.getBody().getPosition().x;
                    float destroyedY = obstacle.getBody().getPosition().y;

                    obstaclesToRemove.add(obstacle);
                    obstacles.remove(obstacle);
                    score += 50;
                    checkBlocksAbove(destroyedX, destroyedY);
                    break;
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
                pigsToRemove.add(pig);
                score+=100;
            }
        } else if (userDataB instanceof Pig && userDataA instanceof Bird) {
            if(userDataA instanceof YellowBird){
                YellowBird yellowBird=(YellowBird) userDataA;
                yellowBird.getBody().setGravityScale(1);
            }
            Pig pig = (Pig) userDataB;
            pig.setHealth(pig.getHealth() - 20);
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
            obstacle.setHealth(obstacle.getHealth() - 20);
            score+=20;
            if (obstacle.getHealth() <= 0) {
                float x = obstacle.getBody().getPosition().x;
                float y = obstacle.getBody().getPosition().y;

                obstaclesToRemove.add(obstacle);
                obstacles.remove(obstacle);
                score += 50;
                checkBlocksAbove(x, y);
            }
        } else if (userDataB instanceof Obstacle && userDataA instanceof Bird) {
            if(userDataA instanceof YellowBird){
                YellowBird yellowBird=(YellowBird) userDataB;
                yellowBird.getBody().setGravityScale(1);
            }
            Obstacle obstacle = (Obstacle) userDataB;
            obstacle.setHealth(obstacle.getHealth() - 20);
            score+=20;
            if (obstacle.getHealth() <= 0) {
                float x = obstacle.getBody().getPosition().x;
                float y = obstacle.getBody().getPosition().y;

                obstaclesToRemove.add(obstacle);
                obstacles.remove(obstacle);
                score += 50;
                checkBlocksAbove(x, y);

            }

        }
        if (userDataA instanceof Pig && userDataB instanceof Obstacle) {
            Pig pig = (Pig) userDataA;
            float impactForce = calculateImpactForce(contact);

            if (impactForce > 5) {
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
        BodyDef leftWallDef = new BodyDef();
        leftWallDef.type = BodyDef.BodyType.StaticBody;
        leftWallDef.position.set(0, viewport.getWorldHeight() / 2);
        Body leftWall = world.createBody(leftWallDef);
        EdgeShape leftEdge = new EdgeShape();
        leftEdge.set(0, -viewport.getWorldHeight() / 2, 0, viewport.getWorldHeight() / 2);
        leftWall.createFixture(leftEdge, 0);
        leftEdge.dispose();

        BodyDef rightWallDef = new BodyDef();
        rightWallDef.type = BodyDef.BodyType.StaticBody;
        rightWallDef.position.set(viewport.getWorldWidth(), viewport.getWorldHeight() / 2);
        Body rightWall = world.createBody(rightWallDef);
        EdgeShape rightEdge = new EdgeShape();
        rightEdge.set(0, -viewport.getWorldHeight() / 2, 0, viewport.getWorldHeight() / 2);
        rightWall.createFixture(rightEdge, 0);
        rightEdge.dispose();

        BodyDef topWallDef = new BodyDef();
        topWallDef.type = BodyDef.BodyType.StaticBody;
        topWallDef.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight());
        Body topWall = world.createBody(topWallDef);
        EdgeShape topEdge = new EdgeShape();
        topEdge.set(-viewport.getWorldWidth() / 2, 0, viewport.getWorldWidth() / 2, 0);
        topWall.createFixture(topEdge, 0);
        topEdge.dispose();
    }

    public ArrayList<Bird> getBirds() {
        return birds;
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