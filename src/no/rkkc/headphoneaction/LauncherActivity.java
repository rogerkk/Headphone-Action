package no.rkkc.headphoneaction;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class LauncherActivity extends android.app.LauncherActivity {
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
    
    class ComparableResolveInfo extends ResolveInfo implements Comparable<ResolveInfo> {

        @Override
        public int compareTo(ResolveInfo another) {
            PackageManager pm = getPackageManager();
            return Collator.getInstance().compare(this.loadLabel(pm), another.loadLabel(pm));
        }
    }
}
