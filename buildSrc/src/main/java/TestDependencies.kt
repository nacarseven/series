import versions.TestVersions
import versions.UiVersions
import versions.CoreVersions

object TestDependencies {
    val junit by lazy { "junit:junit:${TestVersions.junit}" }
    val androidxJunit by lazy { "androidx.test.ext:junit:${TestVersions.androidxJunit}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${TestVersions.espressoCore}" }
}