package kafkalot.common

import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean

import akka.actor.ActorSystem
import akka.event.LoggingAdapter

import scala.concurrent.Await
import scala.concurrent.duration.Duration

abstract class KafkalotApplication {
  private val startLatch = new CountDownLatch(1)
  private val stopLatch = new CountDownLatch(1)
  private val shutdown = new AtomicBoolean(false)

  val applicationName: String
  protected val logger: LoggingAdapter
  protected val config: KafkalotConfig
  protected implicit val system: ActorSystem

  protected def preStart(): Unit
  protected def preStop(): Unit

  def start(): Unit = {
    try {
      logger.info(s"Starting ${applicationName} Application...")

      preStart()

      sys.addShutdownHook {
        try {
          startLatch.await()
          this.stop()
        } catch {
          case e: InterruptedException =>
            logger.error(s"Interrupted waiting for ${applicationName} Application to shutdown")
        }
      }
    } finally {
      startLatch.countDown()
    }
  }

  def stop(): Unit = {
    try {
      logger.info(s"Stopping ${applicationName} Application...")

      val wasShuttingDown = shutdown.getAndSet(true)

      if (!wasShuttingDown) {
        preStop()

        system.terminate()
        Await.result(system.whenTerminated, Duration.Inf)

        logger.info(s"Stopped ${applicationName} Application")
      }
    } finally {
      stopLatch.countDown()
    }
  }
}
