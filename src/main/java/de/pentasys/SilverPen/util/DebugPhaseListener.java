package de.pentasys.SilverPen.util;

import java.util.logging.Logger;

import javax.faces.event.*;
import javax.inject.Inject;



public class DebugPhaseListener implements PhaseListener {

        private static final long serialVersionUID = 8680531759526628407L;
        
        @Inject private Logger lg;
        
        @Override
        public void afterPhase(PhaseEvent event) {
            lg.info("After phase: " + event.getPhaseId());
            
        }
        @Override
        public void beforePhase(PhaseEvent event) {
            lg.info("Before phase: " + event.getPhaseId());
            
        }
        @Override
        public PhaseId getPhaseId() {
            return PhaseId.ANY_PHASE;
        }
      }