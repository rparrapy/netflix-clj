(ns netflix-clj.core
  (:require [clojure.tools.cli :as cli]
            [clojure.string :as string])
  (:gen-class))

(def cli-options
  [["-p" "--path PATH" "Path to the Netflix training directory." :default "training_set"]
   ["-o" "--output OUTPUT" "Path to the output file." :default "out.txt"]
   ["-h" "--help"]])


(defn usage [options-summary]
  (->> ["Simple tools.cli use case: preprocessing for the Netflix movie ratings dataset."
        ""
        "As is, the dataset includes one file per movie."
        "This script merges all files into one single file with each row in the following format:"
        "MovieID[tab]CustomerID[tab]Rating[tab]Date"
        ""
        "Usage: netflix-clj [options]"
        ""
        "Options:"
        options-summary
        "Please refer to the manual page for more information."]
       (string/join \newline)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn process-file [f output]
  (println (str "Processing: " f))
  (let [lines (-> f  clojure.java.io/reader line-seq)
        movieId (apply str (-> lines first butlast))
        ratings (rest lines)]

    (doseq [r ratings] (spit output (str movieId "\t" (string/replace r #"," "\t") "\n") :append true))))

(defn process-dir [path output]
  (def files (-> path clojure.java.io/file file-seq rest))
  (doseq [f files] (process-file f output)))

(defn -main [& args]
  (let [{:keys [options errors summary]} (cli/parse-opts args cli-options)]

    ;; Handle help and error conditions
    (cond
      (:help options) (exit 0 (usage summary))
      errors (exit 1 (error-msg errors)))

    (let [{:keys [path output]} options]
      (process-dir path output))))


