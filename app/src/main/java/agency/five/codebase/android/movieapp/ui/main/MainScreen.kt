package agency.five.codebase.android.movieapp.ui.main

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.destination.MOVIE_ID_KEY
import agency.five.codebase.android.movieapp.destination.MovieDetailsDestination
import agency.five.codebase.android.movieapp.destination.NavigationItem
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesRoute
import agency.five.codebase.android.movieapp.ui.home.HomeRoute
import agency.five.codebase.android.movieapp.ui.moviedetails.MovieDetailsRoute
import agency.five.codebase.android.movieapp.ui.theme.Blue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar by remember {
        derivedStateOf { navBackStackEntry?.destination?.route != "MovieDetails/{movieId}"}
    }
    val showBackIcon = !showBottomBar
    Scaffold(
        topBar = {
            TopBar(
                navigationIcon = {
                    if (showBackIcon) BackIcon(
                        onBackClick = navController::popBackStack,
                        modifier = Modifier.padding(
                            start = dimensionResource(
                                id = R.dimen.medium_spacing
                            )
                        )
                    )
                }
            )
        },
        bottomBar = {
            if (showBottomBar)
                BottomNavigationBar(
                    destinations = listOf(
                        NavigationItem.HomeDestination,
                        NavigationItem.FavoritesDestination,
                    ),
                    onNavigateToDestination = { navigationItem ->
                        navController.popBackStack(
                            NavigationItem.HomeDestination.route,
                            inclusive = false
                        )
                        navController.navigate(navigationItem.route) { launchSingleTop = true }
                    },
                    currentDestination = navBackStackEntry?.destination
                )
        }
    ) { padding ->
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.HomeDestination.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(NavigationItem.HomeDestination.route) {
                    HomeRoute(
                        onNavigateToMovieDetails = { id ->
                            navController.navigate(
                                MovieDetailsDestination.createNavigationRoute(id)
                            )
                        }
                    )
                }
                composable(NavigationItem.FavoritesDestination.route) {
                    FavoritesRoute(
                        onNavigateToMovieDetails = { id ->
                            navController.navigate(
                                MovieDetailsDestination.createNavigationRoute(id)
                            )
                        },
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
                composable(
                    route = MovieDetailsDestination.route,
                    arguments = listOf(navArgument(MOVIE_ID_KEY) { type = NavType.IntType }),
                ) {
                    MovieDetailsRoute()
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    navigationIcon: @Composable (() -> Unit)? = { },
) {
    Box(
        modifier = Modifier
            .background(Blue)
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.top_bar_height)),
        contentAlignment = Alignment.CenterStart
    ) {
        navigationIcon?.invoke()
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun BackIcon(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_back),
        contentDescription = null,
        modifier = modifier.clickable { onBackClick() },
        alignment = Alignment.CenterStart
    )
}

@Composable
private fun BottomNavigationBar(
    destinations: List<NavigationItem>,
    onNavigateToDestination: (NavigationItem) -> Unit,
    currentDestination: NavDestination?,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            destinations.forEach { destination ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (currentDestination != null) {
                        Image(
                            painter = painterResource(
                                id = if (currentDestination.route == destination.route)
                                    destination.selectedIconId
                                else
                                    destination.unselectedIconId
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.back_icon_size))
                                .clickable {
                                    onNavigateToDestination(destination)
                                }
                        )
                    }
                    Text(
                        text = stringResource(id = destination.labelId),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}
