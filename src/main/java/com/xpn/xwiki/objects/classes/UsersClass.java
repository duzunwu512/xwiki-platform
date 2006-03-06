package com.xpn.xwiki.objects.classes;

import com.xpn.xwiki.objects.meta.PropertyMetaClass;
import com.xpn.xwiki.objects.*;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import org.apache.ecs.xhtml.select;
import org.apache.ecs.xhtml.option;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class UsersClass extends ListClass {

    public UsersClass(PropertyMetaClass wclass) {
        super("userslist", "Users List", wclass);
        setSize(6);
    }

    public UsersClass() {
        this(null);
    }

    public List getList(XWikiContext context) {
        List list;
        try {
            list = context.getWiki().getGroupService().listMemberForGroup("XWiki.XWikiAllGroup", context);
        } catch (XWikiException e) {
            // TODO add log exception
            list = new ArrayList();

        }

        list.remove("XWiki.XWikiGuest");
        list.add(0,"XWiki.XWikiGuest");
        return list;
    }

    public BaseProperty newProperty() {
        return new StringProperty();
    }

    public BaseProperty fromString(String value) {
        BaseProperty prop = newProperty();
        prop.setValue(value);
        return prop;
    }

    public BaseProperty fromStringArray(String[] strings) {
        List list = new ArrayList();
        for (int i=0;i<strings.length;i++)
            list.add(strings[i]);
        BaseProperty prop = newProperty();
        prop.setValue(StringUtils.join(list.toArray(), ","));
        return prop;
    }

    public String getText(String value, XWikiContext context) {
        return context.getWiki().getUserName(value, null, false, context);
    }

        public static List getListFromString(String value) {
        List list = new ArrayList();
        if (value==null)
            return list;

        String val = StringUtils.replace(value, "\\,", "%SEP%");
        String[] result = StringUtils.split(value,", ");
        for (int i=0;i<result.length;i++)
            list.add(StringUtils.replace(result[i],"%SEP%", ","));
        return list;
    }

      public void displayEdit(StringBuffer buffer, String name, String prefix, BaseCollection object, XWikiContext context) {
            select select = new select(prefix + name, 1);
            select.setMultiple(isMultiSelect());
            select.setSize(getSize());

            List list = getList(context);
            List selectlist;

            BaseProperty prop =  (BaseProperty)object.safeget(name);
            if (prop==null) {
                selectlist = new ArrayList();
            } else {
                selectlist = getListFromString((String)prop.getValue());
            }

            // Add options from Set
            for (Iterator it=list.iterator();it.hasNext();) {
                String value = it.next().toString();
                option option = new option(value, value);
                option.addElement(getText(value, context));
                if (selectlist.contains(value))
                    option.setSelected(true);
                select.addElement(option);
            }

            buffer.append(select.toString());
    }
}
