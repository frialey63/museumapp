import '@polymer/polymer/polymer-legacy.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import './slider-with-caption.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';

class PlayerControls extends PolymerElement {
  static get template() {
    return html`
   <style>
            :host {
                display: block;
            }

            .margin {
                margin: var(--lumo-space-m);
            }
        </style>

   <slider-with-caption id="positionSlider" caption="[[time]]" value="0" max="1000"></slider-with-caption>

   <vaadin-horizontal-layout theme="spacing margin" style="width: 100%; height: 100%; justify-content: center;">
        <vaadin-button theme="primary" id="back5Button"></vaadin-button>
        <vaadin-button theme="primary" id="stopButton"></vaadin-button>
        <vaadin-button theme="primary" id="pauseButton"></vaadin-button>
        <vaadin-button theme="primary" id="playButton"></vaadin-button>
        <vaadin-button theme="primary" id="forward5Button"></vaadin-button>
   </vaadin-horizontal-layout>
`;
  }

  static get is() {
      return 'player-controls';
  }

  static get properties() {
      return {
          playerStatus: {
              type: String,
              value: ''
          },
          streamStatus: {
              type: String,
              value: ''
          },
          streamName: {
              type: String,
              value: ''
          },
          time: {
              type: String,
              value: '00:00 / 00:00'
          }
      };
  }
}

customElements.define(PlayerControls.is, PlayerControls);

