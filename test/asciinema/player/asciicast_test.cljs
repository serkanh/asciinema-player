(ns asciinema.player.asciicast-test
  (:require-macros [cljs.test :refer (is deftest testing)])
  (:require [cljs.test]
            [asciinema.player.asciicast :as a]))

(deftest load-test
  (testing "pre-rendered, vec"
    (let [asciicast [[1.0 {:lines {:0 [["foo" {}] ["bar" {}]] :1 [["foobar" {}]]}}]
                     [2.0 {:lines {:0 [["baz" {}] ["qux" {}]] :1 [["quuuux" {}]]}}]]
          asciicast (a/load asciicast nil nil)]
      (is (= (select-keys asciicast [:width :height :duration])
             {:width 6 :height 2 :duration 3.0}))))

  (testing "pre-rendered, string"
    (let [asciicast "[[1,{\"lines\":{\"0\":[[\"foo\",{}],[\"bar\",{}]],\"1\":[[\"foobar\",{}]]}}],[2,{\"lines\":{\"0\":[[\"baz\",{}],[\"qux\",{}]],\"1\":[[\"quuuux\",{}]]}}]]"
          asciicast (a/load asciicast nil nil)]
      (is (= (select-keys asciicast [:width :height :duration])
             {:width 6 :height 2 :duration 3.0}))))

  (testing "v1 format, map"
    (let [asciicast {:version 1 :width 80 :height 24 :stdout [[1.0 "foo"] [2.0 "bar"]]}
          asciicast (a/load asciicast nil 30)]
      (is (= (select-keys asciicast [:width :height :duration])
             {:width 80 :height 30 :duration 3.0}))))

  (testing "v1 format, string"
    (let [asciicast "{\"version\":1,\"width\":80,\"height\":24,\"stdout\":[[1,\"foo\"],[2,\"bar\"]]}"
          asciicast (a/load asciicast nil 30)]
      (is (= (select-keys asciicast [:width :height :duration])
             {:width 80 :height 30 :duration 3.0}))))

  (testing "v2 format, vec"
    (let [asciicast [{:version 2 :width 80 :height 24}
                     [1.0 "o" "foo"]
                     [2.0 "i" "iii"]
                     [3.0 "o" "bar"]]
          asciicast (a/load asciicast nil 30)]
      (is (= (select-keys asciicast [:width :height :duration])
             {:width 80 :height 30 :duration 3.0}))))

  (testing "v2 format, string"
    (let [asciicast "[{\"version\":2,\"width\":80,\"height\":24},[1,\"o\",\"foo\"],[2,\"i\",\"iii\"],[3,\"o\",\"bar\"]]"
          asciicast (a/load asciicast nil 30)]
      (is (= (select-keys asciicast [:width :height :duration])
             {:width 80 :height 30 :duration 3.0})))))
