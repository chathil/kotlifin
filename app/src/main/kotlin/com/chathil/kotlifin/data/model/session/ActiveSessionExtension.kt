package com.chathil.kotlifin.data.model.session

fun ActiveSessionProto.toActiveSession(): ActiveSession {
    return ActiveSession(
        userId = user_id,
        username = username,
        serverPublicAddress = server_public_address,
        serverId = server_id,
        serverLocalAddress = server_local_address,
        serverName = server_name,
        accessToken = access_token,
        deviceUuid = device_uuid
    )
}