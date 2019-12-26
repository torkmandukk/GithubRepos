package com.torkmandukk.githubrepos.utils

import com.torkmandukk.githubrepos.models.*
import java.util.Calendar

class MockTestUtil {
    companion object {

        val mockTime = Calendar.getInstance().timeInMillis

        fun mockHistory(): History {
            return History("octocat", mockTime)
        }

        fun mockGithubUser(): GithubUser {
            return GithubUser("octocat", 1000, "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", false,
                    null, null, null, null, null, false, null, 0, 0, 0,
                    0, "", "")
        }

        fun mockRepo(): Repo {
            return Repo()
        }

        fun mockCommit(): GitCommit {
            val parents = mutableListOf<Parent>()
            val author = Author("", "", "")
            val tree = Tree("", "")
            val verification = Verification(false, "", "", "")
            val commitInfo = CommitInfo(author, author, "", tree, "", 0, verification)
            return GitCommit("", "", commitInfo, "", "", "", parents, "", "", 0)
        }
    }
}
