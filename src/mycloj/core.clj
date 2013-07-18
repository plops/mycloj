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


(defn try-scanner-read-line []
  (try
    (core getSerialPortAnswer "COM3" "\n")
    (catch java.lang.Exception e)))

(defn read-scanner []
  (map
    (fn [ign] (try-scanner-read-line))
    (take 3 (iterate inc 1))))
  
(defn sva? [] 
  "check position of the 3 axis piezo stage"
  (core setSerialPortCommand "COM3" "SVA?" "\r")
  (read-scanner))

(while (try-scanner-read-line))


(core setSerialPortCommand "COM3"
      "WGO 2 0\rWAV 1 X SIN 1 100 10 100 0 0 25\rWGO 2 1"
      "\r")
(try-scanner-read-line)


(defn talk-scanner [cmd]
  (core setSerialPortCommand "COM3" cmd "\r"))

(defn wgo? [] 
  (talk-scanner "WGO?")
  (read-scanner))

(defn wgo [n state] 
  (talk-scanner (format "WGO %d %d" n state)))

(wgo?)

(wgo 1 1)

(wgo 2 1)


