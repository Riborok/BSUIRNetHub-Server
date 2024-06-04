package com.bsuirnethub.repository

import com.bsuirnethub.alias.TeacherId
import com.bsuirnethub.entity.TeacherEntity
import com.bsuirnethub.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeacherRepository : JpaRepository<TeacherEntity, Long> {
    fun deleteByTeacherIdAndUser(teacherId: TeacherId, user: UserEntity): Int
}
