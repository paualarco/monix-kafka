package monix.kafka.benchmarks

import monix.reactive.Observable

object Test extends App with KafkaFixture {

  Observable.from("Hello", "World", "!").map(strToProducedRecord).consumeWith(producer)


}
