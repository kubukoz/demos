$version: "2"

namespace demosmithy

enum EntityType {
    CHANNEL_LINEAR
    CHANNEL_XTRA
    EPISODE_AUDIO
    EPISODE_VIDEO
    LINEUP
    SHOW
    SHOW_PODCAST
}

structure Channel {
    @required
    id: String
    @required
    type: EntityType
    @required
    channelField: String
}

structure Show {
    @required
    id: String
    @required
    type: EntityType
    @required
    showField: String
}

structure AudioEpisode {
    @required
    id: String
    @required
    type: EntityType
    @required
    audioField: String
}

structure VideoEpisode {
    @required
    id: String
    @required
    type: EntityType
    @required
    videoField: String
}

structure ChannelLineup {
    @required
    id: String
    @required
    type: EntityType
    @required
    lineupField: String
}
