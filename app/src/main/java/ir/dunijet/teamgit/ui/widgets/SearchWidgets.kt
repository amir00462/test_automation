package ir.dunijet.teamgit.ui.widgets

import android.icu.text.MessagePattern.ApostropheMode
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dev.burnoo.cokoin.navigation.getNavController
import ir.dunijet.teamgit.R
import ir.dunijet.teamgit.data.model.Blog
import ir.dunijet.teamgit.data.model.Category
import ir.dunijet.teamgit.data.model.Filtering
import ir.dunijet.teamgit.ui.theme.cBackground
import ir.dunijet.teamgit.ui.theme.cError
import ir.dunijet.teamgit.ui.theme.cPrimary
import ir.dunijet.teamgit.ui.theme.cTabNotSelected
import ir.dunijet.teamgit.ui.theme.cText1
import ir.dunijet.teamgit.ui.theme.cText2
import ir.dunijet.teamgit.ui.theme.cText3
import ir.dunijet.teamgit.ui.theme.cTextFieldBackground
import ir.dunijet.teamgit.ui.theme.cTextFieldContent
import ir.dunijet.teamgit.ui.theme.cWhite
import ir.dunijet.teamgit.util.Action
import ir.dunijet.teamgit.util.Cache
import ir.dunijet.teamgit.util.KEY_BLOG
import ir.dunijet.teamgit.util.MyScreens
import ir.dunijet.teamgit.util.NO_FILTER
import ir.dunijet.teamgit.util.NetworkChecker
import ir.dunijet.teamgit.util.SITE_BASE_URL
import ir.dunijet.teamgit.util.customTabIndicatorOffset
import ir.dunijet.teamgit.util.getCurrentOrientation
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun SearchToolbar(
    edtValue: String,
    isFilterEnabled: Boolean,
    onEdtChanged: (String) -> Unit,
    onFilterClicked: () -> Unit,
    onBackPressed: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    ConstraintLayout(
        modifier = Modifier.run {
            fillMaxWidth()
                .height(72.dp)
        }
    ) {
        val (backIcon, edt) = createRefs()

        IconButton(modifier = Modifier.constrainAs(backIcon) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }, onClick = { onBackPressed.invoke() }) {
            Icon(
                modifier = Modifier.rotate(180f),
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null
            )
        }

        TextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            textStyle = MaterialTheme.typography.button,
            placeholder = {
                Text(
                    color = cTextFieldContent,
                    text = "عنوان مقاله را جستجو کنید",
                    style = MaterialTheme.typography.button
                )
            },
            value = edtValue,
            singleLine = true,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    tint = cTextFieldContent,
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .size(47.dp)
                        .padding(end = 3.dp)
                        .background(
                            if (isFilterEnabled) cPrimary else Color.White,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onFilterClicked.invoke()
                        }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        tint = if(isFilterEnabled) Color.White else cText1,
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = null,
                    )
                }
            },
            onValueChange = onEdtChanged,
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .height(52.dp)
                .constrainAs(edt) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(end = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = cText1,
                backgroundColor = cTextFieldBackground,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(14.dp),
        )
    }
}

@Composable
fun SearchContent(data: List<Blog>) {
    val context = LocalContext.current
    val navigation = getNavController()

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
                    text = "مقاله\u200Cای با این عنوان وجود ندارد",
                    style = MaterialTheme.typography.h5,
                    color = cText2
                )
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

@Composable
fun CustomCheckBox(title: String, checked: Boolean, onChange: (Action, String) -> Unit) {
    var isChecked by remember { mutableStateOf(checked) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(start = 16.dp, top = 1.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                isChecked = !isChecked
                onChange.invoke(if (isChecked) Action.Inserted else Action.Deleted, title)
            }
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                onChange.invoke(if (isChecked) Action.Inserted else Action.Deleted, title)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = cPrimary,
                uncheckedColor = cText3
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = title, style = MaterialTheme.typography.subtitle2, color = cText1)
        Spacer(modifier = Modifier.width(12.dp))
    }
}

@Composable
fun CategoryPageUi(
    fullList: List<Category>,
    categoryChecked: List<String>,
    onCategoryChanged: (Action, String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(fullList.size) { item ->
            CustomCheckBox(
                title = fullList[item].name,
                checked = categoryChecked.contains(fullList[item].name),
                onChange = onCategoryChanged
            )
        }
    }
}

@Composable
fun AuthorPageUi(
    fullList: List<String>,
    authorNameChecked: List<String>,
    onAuthorChanged: (Action, String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(fullList.size) { item ->
            CustomCheckBox(
                title = fullList[item],
                checked = authorNameChecked.contains(fullList[item]),
                onChange = onAuthorChanged
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun SearchDialog(
    filtering: Filtering,
    categoryList: List<Category>,
    authorList: List<String>,
    onDismissClicked: () -> Unit,
    onSubmitClicked: (Filtering) -> Unit
) {
    val orientation = getCurrentOrientation()

    val tabData = listOf(
        Pair("دسته بندی", R.drawable.dialog_category),
        Pair("نویسندگان", R.drawable.dialog_tag),
    )
    val pagerState = rememberPagerState(
        pageCount = tabData.size,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0,
    )
    val tabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()

    val selectedCategories = remember { mutableStateListOf<String>() }
    val selectedAuthors = remember { mutableStateListOf<String>() }
    selectedCategories.addAll(filtering.categories)
    selectedAuthors.addAll(filtering.authors)

    val currentTabIndex by rememberUpdatedState(newValue = tabIndex)
    Dialog(onDismissRequest = onDismissClicked) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(634.dp),
            shape = RoundedCornerShape(size = 16.dp)
        ) {

            Column(
                modifier = if (orientation == 1) Modifier.verticalScroll(rememberScrollState()) else Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {

                    // TAB
                    TabRow(
                        contentColor = cPrimary,
                        backgroundColor = cWhite,
                        selectedTabIndex = pagerState.currentPage,
                        modifier = Modifier.background(cBackground),
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                modifier = Modifier.customTabIndicatorOffset(
                                    currentTabPosition = tabPositions[tabIndex],
                                    tabWidth = 80.dp,
                                    tabHeight = 3.dp,
                                    topCornerRadius = 4.dp
                                )
                            )
                        },
                        divider = {
                            TabRowDefaults.Divider(
                                thickness = 2.dp,
                                color = cTextFieldBackground
                            )
                        }
                    ) {
                        tabData.forEachIndexed { index, tabPair ->
                            Tab(
                                selected = currentTabIndex == index,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                text = {
                                    Text(
                                        modifier = Modifier.padding(top = 4.dp),
                                        text = tabPair.first,
                                        color = if (tabIndex == index) cPrimary else cTabNotSelected,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.overline,
                                    )
                                },
                                icon = {
                                    Icon(
                                        tint = if (tabIndex == index) cPrimary else cTabNotSelected,
                                        modifier = Modifier.size(20.dp),
                                        painter = painterResource(id = tabPair.second),
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }

                    // PAGER
                    HorizontalPager(
                        state = pagerState
                    ) { index ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(450.dp),
                        ) {

                            if (tabData[index].first == "دسته بندی") {

                                Spacer(modifier = Modifier.height(8.dp))
                                CategoryPageUi(
                                    categoryList,
                                    selectedCategories
                                ) { action, categoryTitle ->

                                    if (action == Action.Inserted)
                                        selectedCategories.add(categoryTitle)
                                    else
                                        selectedCategories.removeIf { it == categoryTitle }

                                }

                            } else {

                                Spacer(modifier = Modifier.height(8.dp))
                                AuthorPageUi(authorList, selectedAuthors) { action, authorName ->
                                    if (action == Action.Inserted)
                                        selectedAuthors.add(authorName)
                                    else
                                        selectedAuthors.removeIf { it == authorName }
                                }

                            }
                        }
                    }

                }

                Column {
                    Button(
                        elevation = ButtonDefaults.elevation(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(), modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .padding(start = 4.dp, end = 4.dp)
                            .border(
                                width = 1.5.dp,
                                color = cTextFieldBackground,
                                shape = RoundedCornerShape(14.dp)
                            ),
                        shape = RoundedCornerShape(14.dp),
                        onClick = {
                            selectedCategories.clear()
                            selectedAuthors.clear()
                            onSubmitClicked.invoke(NO_FILTER)
                            onDismissClicked.invoke()
                        }) {
                        Text(
                            text = "حذف فیلترها",
                            color = cError,
                            style = MaterialTheme.typography.button
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .padding(start = 4.dp, end = 4.dp),
                        shape = RoundedCornerShape(14.dp),
                        onClick = {
                            onSubmitClicked.invoke(
                                Filtering(
                                    categories = selectedCategories,
                                    authors = selectedAuthors
                                )
                            )
                            onDismissClicked.invoke()
                        }) {
                        Text(
                            text = "اعمال فیلترها",
                            color = Color.White,
                            style = MaterialTheme.typography.button
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}