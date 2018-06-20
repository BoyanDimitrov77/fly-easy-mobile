package com.easy.fly.flyeasy.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.activities.MyFlightsActivity;
import com.easy.fly.flyeasy.activities.MyHotelsActivity;
import com.easy.fly.flyeasy.activities.NewsActivity;
import com.easy.fly.flyeasy.activities.SettingActivity;
import com.easy.fly.flyeasy.activities.UserProfileActivity;
import com.easy.fly.flyeasy.common.NewsCountry;
import com.easy.fly.flyeasy.common.NewsParametersConstants;
import com.easy.fly.flyeasy.common.SessionManager;
import com.easy.fly.flyeasy.db.models.UserDB;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class DrawerUtil {

    private static  String accesTokenGD;

    private static String countryCode;

    public static void getDrawerProfileNavigation(final Activity activity, UserDB user, SessionManager sessionManager) {

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem= new PrimaryDrawerItem().withIdentifier(0).withName("");
        drawerEmptyItem.withEnabled(false);

        PrimaryDrawerItem drawerItemPersonaInformation = new PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.personal_information).withIcon(FontAwesome.Icon.faw_user);
        PrimaryDrawerItem drawerItemManageFlightBonus = new PrimaryDrawerItem()
                .withIdentifier(2).withName(R.string.flight_bonus).withIcon(FontAwesome.Icon.faw_trophy);


        PrimaryDrawerItem drawerItemMyFlights = new PrimaryDrawerItem().withIdentifier(3)
                .withName(R.string.my_flights).withIcon(FontAwesome.Icon.faw_plane);

        PrimaryDrawerItem drawerItemDashboard = new PrimaryDrawerItem().withIdentifier(4)
                .withName(R.string.dashboard).withIcon(FontAwesome.Icon.faw_audible);
        PrimaryDrawerItem drawerItemSettings = new PrimaryDrawerItem().withIdentifier(5)
                .withName(R.string.settings).withIcon(FontAwesome.Icon.faw_cog);

        PrimaryDrawerItem drawerItemLogout = new PrimaryDrawerItem().withIdentifier(6)
                .withName(R.string.logout).withIcon(FontAwesome.Icon.faw_sign_out_alt);

        PrimaryDrawerItem drawerItemMyHotels = new PrimaryDrawerItem().withIdentifier(7)
                .withName(R.string.my_hotels).withIcon(FontAwesome.Icon.faw_building);


       System.out.print("UserDB:"+user);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.ic_launcher_background)
                .addProfiles(
                        new ProfileDrawerItem().withName(user== null ? "" :user.getFullName()).withEmail(user== null ? "" : user.getEmail()).withIcon(R.drawable.contacts_icons)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(headerResult)
                //.withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .addDrawerItems(
                        //drawerEmptyItem,drawerEmptyItem,drawerEmptyItem,
                        drawerItemPersonaInformation,
                        //drawerItemManageFlightBonus,
                        drawerItemMyFlights,
                        drawerItemMyHotels,
                       // drawerItemDashboard,
                        //drawerItemAbout,
                        drawerItemSettings,
                        new DividerDrawerItem(),
                        drawerItemLogout
                        //drawerItemHelp,
                        //drawerItemDonate
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {


                        if (drawerItem.getIdentifier() == 1) {

                            Intent intent = new Intent(activity, UserProfileActivity.class);
                            intent.putExtra("KEY","USER_PERSONAL_INFORMATION");
                            view.getContext().startActivity(intent);

                        }else if(drawerItem.getIdentifier() == 2){

                        }else if(drawerItem.getIdentifier() == 3){
                            Intent intent = new Intent(activity, MyFlightsActivity.class);
                            view.getContext().startActivity(intent);
                        }else if(drawerItem.getIdentifier() == 4){

                        }else if(drawerItem.getIdentifier() == 5){
                            Intent intent = new Intent(activity, SettingActivity.class);
                            view.getContext().startActivity(intent);

                        }else if(drawerItem.getIdentifier() == 6){
                            sessionManager.logoutUser();
                        }else if (drawerItem.getIdentifier() == 7){
                            Intent intent = new Intent(activity, MyHotelsActivity.class);
                            view.getContext().startActivity(intent);
                        }
                        return true;
                    }
                })
                .build();
    }

    public static void getDrawerNewsCategoryNavigation(final Activity activity){

        PrimaryDrawerItem businessCategory = new PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.business_category);

        PrimaryDrawerItem entertainmentCategory = new PrimaryDrawerItem().withIdentifier(2)
                .withName(R.string.entertainment_category);

        PrimaryDrawerItem generalCategory = new PrimaryDrawerItem().withIdentifier(3)
                .withName(R.string.general_category);

        PrimaryDrawerItem healthCategory = new PrimaryDrawerItem().withIdentifier(4)
                .withName(R.string.health_category);

        PrimaryDrawerItem scienceCategory = new PrimaryDrawerItem().withIdentifier(5)
                .withName(R.string.science_category);

        PrimaryDrawerItem sportsCategory = new PrimaryDrawerItem().withIdentifier(6)
                .withName(R.string.sports_category);

        PrimaryDrawerItem technologyCategory = new PrimaryDrawerItem().withIdentifier(7)
                .withName(R.string.technology_category);

        PrimaryDrawerItem all = new PrimaryDrawerItem().withIdentifier(8)
                .withName(R.string.all);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.ic_launcher_background)
                .addProfiles(
                        new ProfileDrawerItem().withName(NewsParametersConstants.AUSTRALIA).withIcon(FontAwesome.Icon.faw_globe),
                        new ProfileDrawerItem().withName(NewsParametersConstants.BULGARIA).withIcon(FontAwesome.Icon.faw_globe),
                        new ProfileDrawerItem().withName(NewsParametersConstants.FRANCE).withIcon(FontAwesome.Icon.faw_globe),
                        new ProfileDrawerItem().withName(NewsParametersConstants.GERMANY).withIcon(FontAwesome.Icon.faw_globe),
                        new ProfileDrawerItem().withName(NewsParametersConstants.GREECE).withIcon(FontAwesome.Icon.faw_globe),
                        new ProfileDrawerItem().withName(NewsParametersConstants.RUSSIAN).withIcon(FontAwesome.Icon.faw_globe),
                        new ProfileDrawerItem().withName(NewsParametersConstants.USA).withIcon(FontAwesome.Icon.faw_globe)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        switch(profile.getName().toString()){
                            case NewsParametersConstants.AUSTRALIA :  countryCode = NewsCountry.AUSTRALIA.toString();break;
                            case NewsParametersConstants.BULGARIA :countryCode = NewsCountry.BULGARIA.toString();break;
                            case NewsParametersConstants.FRANCE :countryCode = NewsCountry.FRANCE.toString();break;
                            case NewsParametersConstants.GERMANY :countryCode = NewsCountry.GERMANY.toString();break;
                            case NewsParametersConstants.GREECE : countryCode = NewsCountry.GREECE.toString();break;
                            case NewsParametersConstants.RUSSIAN :countryCode = NewsCountry.RUSSIAN.toString();break;
                            case NewsParametersConstants.USA : countryCode = NewsCountry.USA.toString();break;
                        }
                        return currentProfile;
                    }
                })
                .build();

        //ProfileDrawerItem en = new ProfileDrawerItem().withName("EN");
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .addDrawerItems(
                        businessCategory,
                        entertainmentCategory,
                        generalCategory,
                        healthCategory,
                        scienceCategory,
                        sportsCategory,
                        technologyCategory,
                        all
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        Intent intent = new Intent(activity, NewsActivity.class);
                        if (drawerItem.getIdentifier() == 1) {

                            intent.putExtra("CATEGORY", NewsParametersConstants.BUSINESS_CATEGORY);
                            intent.putExtra("COUNTRY",countryCode);
                            view.getContext().startActivity(intent);

                        }else if(drawerItem.getIdentifier() == 2){

                            intent.putExtra("CATEGORY", NewsParametersConstants.ENTERTAINMENT_CATEGORY);
                            intent.putExtra("COUNTRY",countryCode);
                            view.getContext().startActivity(intent);

                        }else if(drawerItem.getIdentifier() == 3){

                            intent.putExtra("CATEGORY", NewsParametersConstants.GENERAL_CATEGORY);
                            intent.putExtra("COUNTRY",countryCode);
                            view.getContext().startActivity(intent);

                        }else if(drawerItem.getIdentifier() == 4){

                            intent.putExtra("CATEGORY", NewsParametersConstants.HEALTH_CATEGORY);
                            intent.putExtra("COUNTRY",countryCode);
                            view.getContext().startActivity(intent);

                        }else if(drawerItem.getIdentifier() == 5){
                            intent.putExtra("CATEGORY", NewsParametersConstants.SCIENCE_CATEGORY);
                            intent.putExtra("COUNTRY",countryCode);
                            view.getContext().startActivity(intent);

                        }else if(drawerItem.getIdentifier() == 6){
                            intent.putExtra("CATEGORY", NewsParametersConstants.SPORTS_CATEGORY);
                            intent.putExtra("COUNTRY",countryCode);
                            view.getContext().startActivity(intent);

                        }else if(drawerItem.getIdentifier() == 7){
                            intent.putExtra("CATEGORY", NewsParametersConstants.TECHNOLOGY_CATEGORY);
                            intent.putExtra("COUNTRY",countryCode);
                            view.getContext().startActivity(intent);

                        }else if(drawerItem.getIdentifier() == 8){
                            intent.putExtra("CATEGORY", NewsParametersConstants.ALL);
                            intent.putExtra("COUNTRY",countryCode);
                            view.getContext().startActivity(intent);
                        }

                        return true;
                    }
                })
                .build();

    }
}
