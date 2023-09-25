package ir.dunijet.teamgit.ui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Parcelable
import ir.dunijet.teamgit.R
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import dev.burnoo.cokoin.navigation.getNavController
import ir.dunijet.teamgit.data.model.Blog
import ir.dunijet.teamgit.ui.theme.*
import ir.dunijet.teamgit.util.*
import ir.dunijet.teamgit.util.FadeInOutWidget
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SnackBar(title: String) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(true) { isVisible = true }

    FadeInOutWidget(isVisible) {

        Snackbar(
            modifier = Modifier.padding(16.dp),
            backgroundColor = cError
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = TextAlign.Center
            )
        }

        LaunchedEffect(true) {
            delay(2500)
            isVisible = false
        }
    }

}

@Composable
fun HomeToolbar(onSearchClicked: () -> Unit, onDrawerClicked: () -> Unit) {

    ConstraintLayout(
        modifier = Modifier.run {
            fillMaxWidth()
                .height(72.dp)
                .background(cBackground)
        }
    ) {

        val (search, drawer, imgMain) = createRefs()

        Image(
            modifier = Modifier.constrainAs(imgMain) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            painter = painterResource(id = R.drawable.ic_dunijet),
            contentDescription = "dunijet pic"
        )

        MainButton(modifier = Modifier.constrainAs(search) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end, 16.dp)
        }, R.drawable.ic_search) {
            onSearchClicked.invoke()
        }

        MainButton(modifier = Modifier.constrainAs(drawer) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start, 16.dp)
        }, R.drawable.ic_menu) {
            onDrawerClicked.invoke()
        }

    }

}

@Composable
fun HomeDrawer(onCloseDrawer: () -> Unit) {

    BackHandler(onBack = onCloseDrawer)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val (exit, body) = createRefs()

        MainButton(modifier = Modifier.constrainAs(exit) {

            top.linkTo(parent.top, 14.dp)
            end.linkTo(parent.end, 16.dp)

        }, src = R.drawable.ic_close) {
            onCloseDrawer.invoke()
        }

        DrawerBody(modifier = Modifier.constrainAs(body) {
            start.linkTo(parent.start)
            top.linkTo(exit.bottom, 29.dp)
        })

    }

}

@Composable
private fun DrawerMenuItem(
    iconDrawableId: Int,
    text: String
) {

    var rotation by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()
    val rotationAnimation = remember { Animatable(0f) }

    Column {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
//                interactionSource = interactionSource,
//                indication = null
                ) {

                    scope.launch {

                        if (rotation == 0f) {
                            rotation = -90f

                            rotationAnimation.animateTo(
                                targetValue = -90f,
                                animationSpec = tween(durationMillis = 300)
                            )
                        } else {
                            rotation = 0f

                            rotationAnimation.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(durationMillis = 300)
                            )
                        }

                    }

                }
                .padding(top = 14.dp, bottom = 14.dp)
        ) {

            val (title, arrow, detail) = createRefs()

            Row(
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .fillMaxWidth()
                    .padding(start = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(iconDrawableId),
                    tint = cPrimary,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.subtitle2
                )

            }

            Icon(
                modifier = Modifier
                    .constrainAs(arrow) {
                        end.linkTo(parent.end, 25.dp)
                        top.linkTo(title.top)
                        bottom.linkTo(title.bottom)
                    }
                    .size(15.dp, 18.dp)
                    .rotate(rotationAnimation.value),
                tint = cArrow,
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = null,
            )

        }

        AnimatedVisibility(
            visible = rotation == -90f,

            enter = slideInHorizontally(
                initialOffsetX = { 30 },
                animationSpec = tween(durationMillis = 300)
            ) + fadeIn(animationSpec = tween(durationMillis = 300)),

            exit = slideOutVertically(
                targetOffsetY = { 30 },
                animationSpec = tween(durationMillis = 300)
            ) + fadeOut(animationSpec = tween(durationMillis = 300))

        ) {

            if (text == "اطلاعات توسعه دهندگان") {
                DevelopersIds()
            } else {

                AppInfo(
                    modifier = Modifier.padding(
                        top = 8.dp,
                        start = 18.dp,
                        end = 25.dp,
                        bottom = 16.dp
                    )
                )

            }

        }

        //}

    }

}

@Composable
fun DrawerBody(modifier: Modifier) {
    Column(modifier = modifier) {

        DrawerMenuItem(
            iconDrawableId = R.drawable.ic_code,
            text = "اطلاعات توسعه دهندگان"
        )

        DrawerMenuItem(
            iconDrawableId = R.drawable.ic_info,
            text = "درباره برنامه",
        )

    }
}

@Composable
fun AppInfo(modifier: Modifier) {
    val uriHandler = LocalUriHandler.current

    val appInfo = """
        سلام رفقا، صفر تا صد پیاده سازی این این اپ به همراه پیاده سازی بک اند و داشبورد فرانت اند در دوره تیم گیت سایت دانیجت پیاده شده.
        
        در این دوره شما استفاده از گیت و گیت هاب برای مدیریت پروژه در یک تیم برنامه نویسی ۴ نفره به همراه مدیر پروژه و طراح ui رو یاد میگیرید که در آموزش های فارسی گیت بی نظیر و بسیار کارامده.
        این پروژه open source هست و کد اپ و پنل سایت و بک اند رو میتونین در گیت هاب آیدی dunijet به اسم team git پیدا کنین.
        
        برای تهیه این دوره کاربری، دوره تیم گیت رو در اینترنت سرچ کنین یا به سایت دانیجت سر بزنین :)
        
    """.trimIndent()

    Column(modifier = modifier) {

        Text(
            text = appInfo,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.h4
        )

        Text(
            modifier = Modifier
                .padding(start = 18.dp, top = 10.dp)
                .clickable {
                    uriHandler.openUri("https://github.com/dunijet/teamgit-course")
                },
            text = "صفحه گیت هاب پروژه",
            color = cPrimary,
            style = MaterialTheme.typography.h4
        )

        Text(
            modifier = Modifier
                .padding(start = 18.dp, top = 10.dp)
                .clickable {
                    uriHandler.openUri("https://dunijet.ir/product/team-git/")
                },
            text = "ورود به دوره تیم گیت",
            color = cPrimary,
            style = MaterialTheme.typography.h4
        )

        Text(
            modifier = Modifier
                .padding(start = 18.dp, top = 10.dp)
                .clickable {
                    uriHandler.openUri("https://cafebazaar.ir/app/ir.dunijet.teamgit")
                },
            text = "تیم گیت در کافه بازار",
            color = cPrimary,
            style = MaterialTheme.typography.h4
        )

    }
}

@Composable
fun DevelopersIds() {

    Column {
        Developer(R.drawable.amir_mohammadi, "امیرحسین محمدی", "برنامه نویس اپ اندروید", "@dunijet")
        Developer(R.drawable.omid_baharifar, "امید بهاری فر", "برنامه نویس پنل فرانت", "@weblax_ir")
        Developer(R.drawable.erfan_yousefi, "عرفان یوسفی", "برنامه نویس بک اند", "@erfanyousefi.ir")
        Developer(R.drawable.soroush_mosapoor, "سروش موسی\u200Cپور", "متخصص دِوآپس", "@codingcogs")
    }

}

@Composable
private fun Developer(
    iconDrawableId: Int,
    title: String,
    detail: String,
    page: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    val uriHandler = LocalUriHandler.current

    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 18.dp)
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            uriHandler.openUri("https://www.instagram.com/" + page.substring(1))
        }) {

        val (nameDeveloper, imgInsta, details) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(nameDeveloper) {
                    top.linkTo(imgInsta.top)
                    start.linkTo(imgInsta.end)
                }
                .padding(start = 18.dp),
            text = title,
            color = cText1,
            style = MaterialTheme.typography.h4
        )

        CompositionLocalProvider(LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Ltr) {

            Text(
                modifier = Modifier
                    .constrainAs(details) {
                        top.linkTo(nameDeveloper.bottom)
                        start.linkTo(imgInsta.end)
                    }
                    .padding(end = 18.dp, top = 2.dp),
                text = detail + " - " + page,
                color = cText3,
                style = MaterialTheme.typography.overline
            )


        }

        Box(modifier = Modifier
            .constrainAs(imgInsta) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
            .padding(start = 16.dp), contentAlignment = Alignment.Center)
        {

            Image(
                modifier = Modifier.size(52.dp),
                painter = painterResource(id = R.drawable.ic_ring),
                contentDescription = null
            )

            Image(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape),
                painter = painterResource(id = iconDrawableId),
                contentDescription = null
            )

        }

    }

}

// - - - - - - - - - - - - - - - - - - - - -

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(data: List<Blog>, onRequestRefresh: () -> Unit) {
    val navigation = getNavController()
    val context = LocalContext.current

    if (!NetworkChecker(context).isInternetConnected)


        SnackBar(title = "لطفا از اتصال دستگاه خود به اینترنت مطمئن شوید")

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (noBlog, articleList) = createRefs()

        if (data.isEmpty()) {

            Column(modifier = Modifier
                .constrainAs(noBlog) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(id = R.drawable.ic_no_article),
                    contentDescription = null
                )
                Text(
                    text = "مقاله ای برای نمایش وجود ندارد",
                    style = MaterialTheme.typography.h5,
                    color = cText2
                )
                TextButton(onClick = { onRequestRefresh.invoke() }) {
                    Text(
                        text = "بارگذاری مجدد",
                        style = MaterialTheme.typography.caption,
                        color = cText5,
                    )
                }
            }

        } else {

            BlogList(
                modifier = Modifier.constrainAs(articleList) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                data = data
            ) {
                Cache.put(KEY_BLOG, it)
                navigation.navigate(MyScreens.BlogScreen.route)
            }

        }
    }

}


