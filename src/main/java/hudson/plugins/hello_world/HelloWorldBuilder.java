package hudson.plugins.hello_world;

import hudson.Launcher;
import hudson.Extension;
import hudson.model.Build;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import net.sf.json.JSONArray;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.management.Descriptor;


import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

public class HelloWorldBuilder extends Builder {

    private final String name;

    @DataBoundConstructor
    public HelloWorldBuilder(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener)
    {
        if(getDescriptor().useFrench())
        {listener.getLogger().println("Bonjour, "+name+"!");

        String jsonData1 = this.readFile("path.json");
            listener.getLogger().println("jsondata1: "+jsonData1);
        String jsonData = "{\"one\":\"won\",\"two\":2,\"three\":false}";
        listener.getLogger().println("jsonData: "+jsonData);
        JSONObject jobj = JSONObject.fromObject(jsonData);
        Map map = jobj;
        System.out.println(map);
        //jobj.fromObject(jsonData);
        /*JSONArray jarr = new JSONArray();
        jarr.fromObject(jobj.getJSONArray("steps"));
            listener.getLogger().println("Name: " + jobj.getString("name"));
        for(int i = 0; i < jarr.size(); i++)
        {
            System.out.println(i+": " + jarr.getString(i));
        }*/
        }
        return true;
    }

    @Override
    public DescriptorImpl getDescriptor()
    {
        return (DescriptorImpl)super.getDescriptor();
    }

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder>
    {
        private boolean useFrench;

        public DescriptorImpl()
        {
            load();
        }

        @Override
        public String getDisplayName()
        {
            return "Say hello worldddd";
        }

        @Override
        public boolean isApplicable(Class type)
        {
            return true;
        }

        @Override
        public boolean configure(StaplerRequest staplerRequest, JSONObject json) throws FormException
        {
            useFrench = json.getBoolean("useFrench");
            save();
            return true;
        }

        public boolean useFrench()
        {
            return useFrench;
        }
    }
}
