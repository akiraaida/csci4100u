package ca.uoit.csci4100u.samplemidterm;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DownloadFeedTask extends AsyncTask<String, Void, ArrayList<Story>> {

    private StoryDataListener storyDataListener;

    public void setStoryDataListener(StoryDataListener storyDataListener) {
        this.storyDataListener = storyDataListener;
    }

    @Override
    protected ArrayList<Story> doInBackground(String... strings) {
        ArrayList<Story> stories = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.newDocumentBuilder();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            Log.d("InternetResourcesSample", "url: " + strings[0]);
            URL url = new URL(strings[0]);
            Document document = docBuilder.parse(url.openStream());
            document.getDocumentElement().normalize();

            NodeList main = document.getElementsByTagName("feed");
            if ((main.getLength() > 0) && (main.item(0).getNodeType() == Node.ELEMENT_NODE)) {
                Element definitions = (Element)main.item(0);
                NodeList entryTags = definitions.getElementsByTagName("entry");
                for (int i = 0; i < entryTags.getLength(); i++) {
                    Element info = (Element)entryTags.item(i);
                    String author = info.getElementsByTagName("author").item(0).getTextContent();
                    String title = info.getElementsByTagName("title").item(0).getTextContent();
                    String summary = info.getElementsByTagName("summary").item(0).getTextContent();
                    stories.add(new Story(title, author, summary));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stories;
    }

    protected void onPostExecute(ArrayList<Story> data) {
        storyDataListener.showStories(data);
    }
}
