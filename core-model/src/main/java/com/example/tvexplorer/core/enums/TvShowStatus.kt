package com.example.tvexplorer.core.enums

enum class TvShowStatus(val identifier: String) {
    Unknown("Unknown"),
    Ended("Ended"),
    InDevelopment("In Development"),
    ToBeDetermined("To Be Determined"),
    Running("Running");

    companion object {
        fun fromIdentifier(identifier: String?): TvShowStatus {
            if (identifier != null)
                for (value in TvShowStatus.values())
                    if (value.identifier.equals(identifier, true))
                        return value

            assert(true) { "Identifier $identifier does not match any of the values" }
            return Unknown
        }
    }
}