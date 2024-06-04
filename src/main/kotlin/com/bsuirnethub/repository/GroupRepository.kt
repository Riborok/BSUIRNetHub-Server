package com.bsuirnethub.repository

import com.bsuirnethub.alias.GroupId
import com.bsuirnethub.entity.GroupEntity
import com.bsuirnethub.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<GroupEntity, Long> {
    fun deleteByGroupIdAndUser(groupId: GroupId, user: UserEntity): Int
}
