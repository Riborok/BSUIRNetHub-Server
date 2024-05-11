package com.bsuirnethub.model

import com.bsuirnethub.entity.SubscriberEntity

class Subscriber(
    var subscriberId: String? = null,
) {
    companion object {
        fun toModel(entity: SubscriberEntity): Subscriber {
            return Subscriber(entity.subscriberId)
        }
    }
}
