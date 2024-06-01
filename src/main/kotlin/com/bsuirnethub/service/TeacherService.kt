package com.bsuirnethub.service

import com.bsuirnethub.alias.TeacherId
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.finder.UserFinder
import com.bsuirnethub.validator.TeacherValidator
import com.bsuirnethub.entity.TeacherEntity
import com.bsuirnethub.repository.TeacherRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TeacherService(
    private val userFinder: UserFinder,
    private val teacherRepository: TeacherRepository,
    private val teacherValidator: TeacherValidator
) {
    fun addTeacher(userId: UserId, teacherId: TeacherId): TeacherId? {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val teacherEntity = TeacherEntity(teacherId = teacherId, user = userEntity)
        return teacherValidator.validateTeacherDoesNotExists(userId, teacherId) {
            val savedTeacherEntity = teacherRepository.save(teacherEntity)
            savedTeacherEntity.teacherId
        }
    }

    fun deleteTeacher(userId: UserId, teacherId: TeacherId) {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val deletedCount = teacherRepository.deleteByTeacherIdAndUser(teacherId, userEntity)
        teacherValidator.validateTeacherDeletion(deletedCount, userId, teacherId)
    }

    fun getTeacherIds(userId: UserId): List<TeacherId?> {
        val userEntity = userFinder.findUserEntityByIdOrThrow(userId)
        val teacherEntities = userEntity.teachers
        return teacherEntities.map { it.teacherId }
    }
}