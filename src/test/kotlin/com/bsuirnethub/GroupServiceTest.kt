package com.bsuirnethub

import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.repository.GroupRepository
import com.bsuirnethub.repository.UserRepository
import com.bsuirnethub.service.GroupService
import com.bsuirnethub.service.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GroupServiceTest(
    @Autowired val userRepository: UserRepository,
    @Autowired private val groupRepository: GroupRepository,
    @Autowired val userService: UserService,
    @Autowired private val groupService: GroupService
) {
    @BeforeEach
    fun setUp() {
        groupRepository.deleteAll()
        userRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        groupRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun `test addGroup`() {
        val userId = "user"
        val groupId = "group"
        userService.createUser(userId)
        groupService.addGroup(userId, groupId)
        val groups = groupService.getGroupIds(userId)
        assertEquals(1, groups.size)
        assertEquals(groupId, groups[0])
    }

    @Test
    fun `test addGroup with existing group`() {
        val userId = "user"
        val groupId = "group"
        userService.createUser(userId)
        groupService.addGroup(userId, groupId)
        assertThrows<RestStatusException> {
            groupService.addGroup(userId, groupId)
        }
    }

    @Test
    fun `test deleteGroup`() {
        val userId = "user"
        val groupId = "group"
        userService.createUser(userId)
        groupService.addGroup(userId, groupId)
        groupService.deleteGroup(userId, groupId)
        val groupIds = groupService.getGroupIds(userId)
        assertFalse(groupIds.contains(groupId))
    }

    @Test
    fun `test deleteGroup with non-existent group`() {
        val userId = "user"
        val groupId = "group"
        userService.createUser(userId)
        assertThrows<RestStatusException> {
            groupService.deleteGroup(userId, groupId)
        }
    }

    @Test
    fun `test getGroupIds`() {
        val userId = "user"
        userService.createUser(userId)
        val groupIdsToAdd = List(42) { "group$it" }
        groupIdsToAdd.forEach { groupService.addGroup(userId, it) }
        val groupIds = groupService.getGroupIds(userId)
        assertEquals(42, groupIds.size)
        assertTrue(groupIds.containsAll(groupIdsToAdd))
    }
}
