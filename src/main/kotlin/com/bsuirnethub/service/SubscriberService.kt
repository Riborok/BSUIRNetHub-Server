package com.bsuirnethub.service

import com.bsuirnethub.entity.SubscriberEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.model.Subscriber
import com.bsuirnethub.repository.SubscriberRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class SubscriberService(
    private val userService: UserService,
    private val subscriberRepository: SubscriberRepository
) {
    fun getAllSubscribers(userId: String): List<Subscriber> {
        val user = userService.findUserOrThrow(userId)
        return user.subscribers.map(Subscriber::toModel)
    }

    fun deleteSubscriber(userId: String, subscriberId: String) {
        val user = userService.findUserOrThrow(userId)
        val subscriber = findSubscriberOrThrow(user, subscriberId)
        deleteSubscriber(subscriber)
    }

    fun findSubscriberOrThrow(user: UserEntity, subscriberId: String): SubscriberEntity {
        return subscriberRepository.findByUserAndSubscriberId(user, subscriberId)
            ?: throw RestStatusException("Subscriber with id $subscriberId not found for user ${user.userId}", HttpStatus.NOT_FOUND)
    }

    private fun deleteSubscriber(subscriber: SubscriberEntity) {
        subscriberRepository.delete(subscriber)
    }

    fun addSubscriber(userId: String, subscriberId: String): SubscriberEntity {
        val user = userService.findUserOrThrow(userId)
        validateSubscriberExists(subscriberId)
        validateSubscriberIsNotUser(userId, subscriberId)
        validateSubscriberNotExist(user, subscriberId)
        return saveNewSubscriber(user, subscriberId)
    }

    private fun validateSubscriberExists(subscriberId: String) {
        userService.findUserOrThrow(subscriberId)
    }

    private fun validateSubscriberIsNotUser(userId: String, subscriberId: String) {
        if (userId == subscriberId) {
            throw RestStatusException("Cannot add yourself as a subscriber", HttpStatus.BAD_REQUEST)
        }
    }

    private fun validateSubscriberNotExist(user: UserEntity, subscriberId: String) {
        val existingSubscriber = subscriberRepository.findByUserAndSubscriberId(user, subscriberId)
        if (existingSubscriber != null) {
            throw RestStatusException("Subscriber with id $subscriberId already exists for user ${user.userId}", HttpStatus.BAD_REQUEST)
        }
    }

    private fun saveNewSubscriber(user: UserEntity, subscriberId: String): SubscriberEntity {
        val newSubscriber = SubscriberEntity(subscriberId = subscriberId, user = user)
        return subscriberRepository.save(newSubscriber)
    }
}
