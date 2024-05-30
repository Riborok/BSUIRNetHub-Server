package com.bsuirnethub

import com.bsuirnethub.component.DatabaseCleanup
import com.bsuirnethub.component.UserInitializer
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.service.GroupService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GroupServiceTest(
    @Autowired private val groupService: GroupService,
    @Autowired val userInitializer: UserInitializer,
    @Autowired private val databaseCleanup: DatabaseCleanup
) {
    @BeforeEach
    fun setUp() {
        databaseCleanup.clearDatabase()
    }

    @AfterEach
    fun tearDown() {
        databaseCleanup.clearDatabase()
    }

    private val groupId = "group"

    @Test
    fun `test addGroup`() {
        val userIds = userInitializer.createAndSaveUsers(1).userIds
        val userId = userIds[0]
        groupService.addGroup(userId, groupId)
        val groupIds = groupService.getGroupIds(userId)
        assertEquals(1, groupIds.size)
        assertEquals(groupId, groupIds[0])
    }

    @Test
    fun `test addGroup With Existing Group`() {
        val userIds = userInitializer.createAndSaveUsers(1).userIds
        val userId = userIds[0]
        groupService.addGroup(userId, groupId)
        assertThrows<RestStatusException> {
            groupService.addGroup(userId, groupId)
        }
    }

    @Test
    fun `test deleteGroup`() {
        val userIds = userInitializer.createAndSaveUsers(1).userIds
        val userId = userIds[0]
        groupService.addGroup(userId, groupId)
        groupService.deleteGroup(userId, groupId)
        val groupIds = groupService.getGroupIds(userId)
        assertFalse(groupIds.contains(groupId))
    }

    @Test
    fun `test deleteGroup With Non-Existent Group`() {
        val userIds = userInitializer.createAndSaveUsers(1).userIds
        val userId = userIds[0]
        assertThrows<RestStatusException> {
            groupService.deleteGroup(userId, groupId)
        }
    }

    @Test
    fun `test getGroupIds`() {
        val userIds = userInitializer.createAndSaveUsers(1).userIds
        val userId = userIds[0]
        val groupIdsToAdd = List(42) { "group$it" }
        groupIdsToAdd.forEach { groupService.addGroup(userId, it) }
        val groupIds = groupService.getGroupIds(userId)
        assertEquals(42, groupIds.size)
        assertTrue(groupIds.containsAll(groupIdsToAdd))
    }
}
