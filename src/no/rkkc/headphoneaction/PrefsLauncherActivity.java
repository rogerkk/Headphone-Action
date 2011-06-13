package no.rkkc.headphoneaction;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.widget.ListView;

public class PrefsLauncherActivity extends android.app.LauncherActivity {
    @Override
    public List<ListItem> makeListItems() {
        List<ListItem> items = new ArrayList<ListItem>();
        
        final PackageManager pm = getPackageManager();
        
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        
        List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(mainIntent, 0);
        Collections.sort(resolveInfoList, new ResolveInfo.DisplayNameComparator(pm));
        
        for (ResolveInfo resolveInfo: resolveInfoList) {
            ListItem item = new ListItem();
            
            item.packageName = resolveInfo.activityInfo.packageName;
            item.className = resolveInfo.activityInfo.name;
            item.icon = resolveInfo.loadIcon(pm);
            item.label = resolveInfo.loadLabel(pm);
            item.resolveInfo = resolveInfo;
            
            items.add(item);
        }
              
        return items;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ListItem item = itemForPosition(position);
        
        // Return info about the selected app/activity
        Intent intent = new Intent();
        intent.putExtra("appPackage", item.resolveInfo.activityInfo.packageName);
        intent.putExtra("appClass", item.resolveInfo.activityInfo.name);
        intent.putExtra("appName", item.resolveInfo.loadLabel(getPackageManager()));
        
        setResult(RESULT_OK, intent);
        finish();
    }
}
