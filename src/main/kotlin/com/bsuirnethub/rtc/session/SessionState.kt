package com.bsuirnethub.rtc.session

enum class SessionState {
    Active, // Offer and Answer has been sent
    Creating, // Creating session, offer has been sent
    Ready, // Both clients available and ready to initiate session
    Impossible // Second user is offline
}