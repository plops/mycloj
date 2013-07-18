(ns mycloj.core
  (:import  (org.micromanager MMStudioMainFrame))
  (:use [org.micromanager.mm :only
          [when-lets map-config get-config get-positions load-mm
           get-default-devices core log log-cmd mmc gui with-core-setting
           do-when if-args get-system-config-cached select-values-match?
           get-property get-camera-roi parse-core-metadata reload-device
           json-to-data get-pixel-type get-msp-z-position set-msp-z-position
           get-msp MultiStagePosition-to-map ChannelSpec-to-map
           get-pixel-type get-current-time-str rekey
           data-object-to-map str-vector double-vector
           get-property-value edt attempt-all]]))

;; initially i read the following code for an overview of how to use clojure in mm
;https://valelab.ucsf.edu/svn/micromanager2/trunk/acqEngine/src/org/micromanager/acq_engine.clj

;; this file seems to define a few useful functions:
;; https://valelab.ucsf.edu/svn/micromanager2/trunk/acqEngine/src/org/micromanager/mm.clj


(load-mm (MMStudioMainFrame/getInstance))

(core setSerialPortCommand "COM3" "SVA?" "\r")
(map
  (fn [ign] (core getSerialPortAnswer "COM3" "\n"))
  (take 3 (iterate inc 1)))



