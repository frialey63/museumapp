package org.pjp.museum.ui.view.exhibit;

import org.pjp.museum.ui.component.slider.SliderWithCaption;
import org.vaadin.addon.audio.server.AudioPlayer;
import org.vaadin.addon.audio.server.state.PlaybackState;
import org.vaadin.addon.audio.server.state.StateChangeCallback;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * A Designer generated component for the player-controls template.
 * <p>
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("player-controls")
@JsModule("./src/player-controls.js")
@Uses(SliderWithCaption.class)
public class PlayerControls extends PolymerTemplate<PlayerControls.PlayerControlsModel> implements HasSize, HasComponents {

    private static final long serialVersionUID = 908526599880999031L;

    /**
     * This model binds properties between PlayerControls and player-controls
     */
    public interface PlayerControlsModel extends TemplateModel {
        String getPlayerStatus();

        void setPlayerStatus(String playerStatus);

        String getStreamStatus();

        void setStreamStatus(String streamStatus);

        String getStreamName();

        void setStreamName(String streamName);

        String getTime();

        void setTime(String time);
    }

    @Id("back5Button")
    private Button back5Button;
    @Id("stopButton")
    private Button stopButton;
    @Id("pauseButton")
    private Button pauseButton;
    @Id("playButton")
    private Button playButton;
    @Id("forward5Button")
    private Button forward5Button;
    @Id("positionSlider")
    private SliderWithCaption positionSlider;

    private AudioPlayer player;

    /**
     * Creates a new PlayerControls.
     */
    public PlayerControls() {
        setWidthFull();

        positionSlider.getSlider().addValueChangeListener(e -> {
            if (e.isFromClient()) {
                player.setPosition(e.getValue().intValue());
                getModel().setTime(player.getPositionString() + " / " + player.getDurationString());
            }
        });

        back5Button.addClickListener(e -> {
            //player.skip(-5000);
            int pos = Math.max(0, player.getPosition() - 5000);
            player.setPosition(pos);
            updateSlider(pos);
        });
        back5Button.setIcon(VaadinIcon.FAST_BACKWARD.create());

        stopButton.addClickListener(e -> {
            player.stop();
        });
        stopButton.setIcon(VaadinIcon.STOP.create());

        pauseButton.addClickListener(e -> {
            if (player.isPaused()) {
                player.resume();
            } else {
                player.pause();
            }
        });
        pauseButton.setIcon(VaadinIcon.PAUSE.create());

        playButton.addClickListener(e -> {
            if (player.isStopped()) {
                player.play();
            } else if (player.isPaused()) {
                player.resume();
            } else {
                // player.play(0);
                player.play();
            }
        });
        playButton.setIcon(VaadinIcon.PLAY.create());

        forward5Button.addClickListener(e -> {
            //player.skip(5000);
            int pos = Math.min(player.getDuration(), player.getPosition() + 5000);
            player.setPosition(pos);
            updateSlider(pos);
        });
        forward5Button.setIcon(VaadinIcon.FAST_FORWARD.create());
    }

    public void setPlayer(AudioPlayer player, String streamName) {
        this.player = player;

        getElement().appendChild(player.getElement());

        final UI ui = UI.getCurrent();

        player.getStream().addStateChangeListener(newState -> {
            ui.access(() -> {
                String text = "Stream status: ";
                switch (newState) {
                    case COMPRESSING:
                        text += "COMPRESSING";
                        break;
                    case ENCODING:
                        text += "ENCODING";
                        break;
                    case IDLE:
                        text += "IDLE";
                        break;
                    case READING:
                        text += "READING";
                        break;
                    case SERIALIZING:
                        text += "SERIALIZING";
                        break;
                    default:
                        text += "broken or something";
                        break;
                }
                getModel().setStreamStatus(text);
            });
        });

        player.addStateChangeListener(new StateChangeCallback() {

            {
                modeButtons(PlaybackState.STOPPED);
            }

            @Override
            public void playbackStateChanged(final PlaybackState newState) {
                ui.access(() -> {
                    modeButtons(newState);

                    String text = "Player status: ";
                    switch (newState) {
                        case PAUSED:
                            text += "PAUSED";
                            break;
                        case PLAYING:
                            text += "PLAYING";
                            break;
                        case STOPPED:
                            text += "STOPPED";
                            break;
                        default:
                            break;
                    }
                    getModel().setPlayerStatus(text);
                });
            }

            @Override
            public void playbackPositionChanged(final int newPositionMillis) {
                ui.access(() -> {
                    // TODO for proper slider setting, we need to know the position in millis and total duration of audio
                    updateSlider(newPositionMillis);
                });
            }

            private void modeButtons(PlaybackState newState) {
                switch (newState) {
                case PAUSED:
                    stopButton.setEnabled(true);
                    playButton.setEnabled(false);
                    pauseButton.setEnabled(true);
                    break;
                case PLAYING:
                    stopButton.setEnabled(true);
                    playButton.setEnabled(false);
                    pauseButton.setEnabled(true);
                    break;
                case STOPPED:
                    stopButton.setEnabled(false);
                    playButton.setEnabled(true);
                    pauseButton.setEnabled(false);
                    break;
                default:
                    break;
                }
            }
        });

        getModel().setStreamName("Stream " + streamName);
    }

    public void initPositionSlider() {
        int duration = player.getDuration();

        positionSlider.getSlider().setMaxValue(duration);
        positionSlider.getSlider().setMinValue(0.0);
        positionSlider.getSlider().setValue(0.0);
    }

    private void updateSlider(int newPositionMillis) {
        int duration = player.getDuration();
        int pos = player.getPosition();

        positionSlider.getSlider().setMaxValue(duration);
        positionSlider.getSlider().setMinValue(0.0);
        // set value without trigger value change event
        positionSlider.getSlider().setValue((double) newPositionMillis);

        getModel().setTime(player.getPositionString() + " / " + player.getDurationString());
    }
}
