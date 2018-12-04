/**
 * Exercise 4
 *
 * In this exercise we will learn how to use Scala traits
 * to create modular behaviors that will allow you to
 * mix in behaviors into your actors.
 * Objective is to learn how to pass messages around and preserve
 * the sender of a message.
 */

package ua.ucu.edu

import java.util.UUID

import akka.actor._
import akka.actor.Actor._

/**
 * Below is the blueprint of our soon to be MailmanActor and RecipientActor,
 * this is where you will implement the behavior of the actor.
 * We will also learn to use 'forward' to preserve the original
 * sender of messages.
 *
 * Underneath the RecipientActor-class is the unit-test you will create
 * to verify your implementation.
 */

case class Parcel(recipient: UUID, contents: AnyRef)

object MailmanActor {
  sealed trait MailmanMessage
  case class RegisterRecipient(recipient: ActorRef) extends MailmanMessage
  case class DeregisterRecipient(recipient: ActorRef) extends MailmanMessage
  case class ReturnToSender(parcel: Parcel) extends MailmanMessage

  trait RegistrationBehavior { actor: Actor =>
    var registeredRecipients: Map[UUID, ActorRef] = Map[UUID, ActorRef]()

    //TODO: Implement this method
    def handleRegistrations: Receive
    //RegisterRecipient messages should register recipients
    //DeregisterRecipient messages should deregister recipients

    //TODO: Implement this method
    def findRecipientFor(recipientUuid: UUID): Option[ActorRef]
  }

  trait ParcelManagement { actor: Actor =>

    def findRecipientFor(recipientUuid: UUID): Option[ActorRef] //You will not need to implement this

    //TODO: Implement the following method
    def handleParcel: Receive
    //Parcel should be forwarded to the correct recipient (if found) else reply with a ReturnToSender
  }
}

import MailmanActor._
class MailmanActor { //TODO: Make this into an Actor that utilizes your traits
  //TODO: Implement the receive method
}

object RecipientActor {
  sealed trait RecipientMessage
  case class ThanksForThePresent(contents: AnyRef) extends RecipientMessage
}

class RecipientActor { //TODO: Make this an Actor
  import RecipientActor._

  //TODO: Implement receive to only receive parcels meant for this actor,
  //      and put the parcels at the head of the 'receivedParcels' list and respond
  //      with a ThanksForThePresent message with the contents of the parcel
}

/**
* The following test verifies that the functionality
* of the code you've written works as expected
*/

class MailmanActorSpec extends BaseAkkaSpec(ActorSystem("test-system")) {
  import MailmanActor._
  import RecipientActor._


  "Exercise4" should {
    "teach you how to route messages to other actors and preserve the sender" in {
      //TODO: Create a MailmanActor

      //TODO: Create a RecipientActor

      //TODO: Create a parcel, addressed to the recipient, to the mailman and make sure you get a ReturnToSender with the same parcel

      //TODO: Register the recipient to the mailman

      //TODO: Send a parcel, addressed to the recipient, to the mailman and
      // make sure you get a ThanksForThePresent back with the expected contents

      //TODO: Now de-register the recipient from the mailman

      //TODO: Then verify that sending a parcel, addressed to the recipient, to the mailman yields the expected ReturnToSender

      //TODO: Stop both the mailman and the recipient
    }
  }
}