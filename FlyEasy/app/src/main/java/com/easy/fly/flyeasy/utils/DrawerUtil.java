package com.easy.fly.flyeasy.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.easy.fly.flyeasy.R;
import com.easy.fly.flyeasy.activities.SettingActivity;
import com.easy.fly.flyeasy.activities.UserProfileActivity;
import com.easy.fly.flyeasy.db.models.User;
import com.easy.fly.flyeasy.db.models.UserDB;
import com.easy.fly.flyeasy.fragments.UserPersonalInformationFragment;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class DrawerUtil {

    private static  String accesTokenGD;

    public static void getDrawer(final Activity activity, UserDB user) {

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem= new PrimaryDrawerItem().withIdentifier(0).withName("");
        drawerEmptyItem.withEnabled(false);

        PrimaryDrawerItem drawerItemPersonaInformation = new PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.personal_information).withIcon(FontAwesome.Icon.faw_user);
        PrimaryDrawerItem drawerItemManageFlightBonus = new PrimaryDrawerItem()
                .withIdentifier(2).withName(R.string.flight_bonus).withIcon(FontAwesome.Icon.faw_trophy);


        SecondaryDrawerItem drawerItemMyFlights = new SecondaryDrawerItem().withIdentifier(3)
                .withName(R.string.my_flights).withIcon(FontAwesome.Icon.faw_plane);

        SecondaryDrawerItem drawerItemDashboard = new SecondaryDrawerItem().withIdentifier(4)
                .withName(R.string.dashboard).withIcon(FontAwesome.Icon.faw_audible);
        SecondaryDrawerItem drawerItemSettings = new SecondaryDrawerItem().withIdentifier(5)
                .withName(R.string.settings).withIcon(FontAwesome.Icon.faw_cog);

       System.out.print("UserDB:"+user);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.ic_launcher_background)
                .addProfiles(
                        new ProfileDrawerItem().withName(user.getFullName()== null ? "" :user.getFullName()).withEmail(user.getEmail()== null ? "" : user.getEmail() ).withIcon(R.drawable.contacts_icons)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        /*DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Log.d("URI",uri.toString());
                Glide.with(imageView.getContext()).load(HeaderAtuhenticationGlide.loadUrl(uri.toString(),userAthenticationHeader)).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.with(imageView.getContext()).clear(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx, String tag) {
                //define different placeholders for different imageView targets
                //default tags are accessible via the DrawerImageLoader.Tags
                //custom ones can be checked via string. see the CustomUrlBasePrimaryDrawerItem LINE 111
                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(ctx);
                } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(56);
                } else if ("customUrlItem".equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(56);
                }

                //we use the default one for
                //DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name()

                return super.placeholder(ctx, tag);
            }
        });*/



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
                        drawerItemManageFlightBonus,
                        drawerItemMyFlights,
                        drawerItemDashboard,
                        new DividerDrawerItem(),
                        //drawerItemAbout,
                        drawerItemSettings
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

                        }else if(drawerItem.getIdentifier() == 4){

                        }else if(drawerItem.getIdentifier() == 5){
                            Intent intent = new Intent(activity, SettingActivity.class);
                            view.getContext().startActivity(intent);

                        }
                        return true;
                    }
                })
                .build();
    }
}
