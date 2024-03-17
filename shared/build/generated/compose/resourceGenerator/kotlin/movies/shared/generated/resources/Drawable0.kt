@file:OptIn(org.jetbrains.compose.resources.InternalResourceApi::class)

package movies.shared.generated.resources

import kotlin.OptIn
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

@ExperimentalResourceApi
private object Drawable0 {
  public val ic_movies_512: DrawableResource = org.jetbrains.compose.resources.DrawableResource(
        "drawable:ic_movies_512",
          setOf(
            org.jetbrains.compose.resources.ResourceItem(setOf(), "drawable/ic_movies_512.png"),
          )
      )
}

@ExperimentalResourceApi
internal val Res.drawable.ic_movies_512: DrawableResource
  get() = Drawable0.ic_movies_512
