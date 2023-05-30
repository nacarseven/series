import versions.CoreVersions

object CoreDependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${CoreVersions.coreKtx}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${CoreVersions.retrofit}" }
    val retrofitSerialization by lazy { "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${CoreVersions.retrofitSerialization}" }
    val okhttp3 by lazy { "com.squareup.okhttp3:logging-interceptor:${CoreVersions.okhttp3}" }
    val jsonSerialization by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:${CoreVersions.jsonSerialization}" }
    val coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${CoreVersions.coroutines}" }
    val koin by lazy { "io.insert-koin:koin-android:${CoreVersions.koin}" }
}