package com.bsuirnethub.repository

import com.bsuirnethub.alias.TeacherId
import com.bsuirnethub.entity.TeacherEntity
import com.bsuirnethub.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository : JpaRepository<TeacherEntity, Long> {
    fun deleteByTeacherIdAndUser(teacherId: TeacherId, user: UserEntity): Int
}
