package com.tiger.bb_nt.util;

import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.model.bb.Player;
import com.tiger.bb_nt.model.bb.Skill;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XMLUtils {

    
    
    public static Document getDocument(String s){
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document doc = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(new InputSource(new StringReader(s)));
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return doc;
    }
    
    public static List<Player> parsePlayers(Document document, boolean addSkills, User user){
        List<Player> players=new ArrayList<>();
        for (int i = 0; i < document.getElementsByTagName("player").getLength(); i++) {
            Player player=new Player();
            player.setAge(Integer.valueOf(document.getElementsByTagName("age").item(i).getTextContent().trim()));
            player.setDmi(Integer.valueOf(document.getElementsByTagName("dmi").item(i).getTextContent().trim()));
            player.setFirstName(document.getElementsByTagName("firstName").item(i).getTextContent().trim());
            player.setGameShape(Integer.valueOf(document.getElementsByTagName("gameShape").item(i).getTextContent().trim()));
            Integer bbHeight=Integer.valueOf(document.getElementsByTagName("height").item(i).getTextContent().trim());
            player.setHeight(Math.toIntExact(Math.round(bbHeight * 30.5 / 12)));
            player.setId(document.getElementsByTagName("player").item(i).getAttributes().getNamedItem("id").getNodeValue());
            player.setLastName(document.getElementsByTagName("lastName").item(i).getTextContent().trim());
            player.setManager(user.getAlias());
            player.setNationality(document.getElementsByTagName("nationality").item(i).getTextContent().trim());
            player.setPotential(Integer.valueOf(document.getElementsByTagName("potential").item(i).getTextContent().trim()));
            player.setTeam(user.getTeam());
            player.setSalary(Integer.valueOf(document.getElementsByTagName("salary").item(i).getTextContent().trim()));
            if (addSkills)
                setSkills(player, document, i);
            players.add(player);
        }
        
        return players;
    }

    private static void setSkills(Player player, Document document, int i) {
        Map<Skill, Integer> skills=SkillInitializer.initialize();
        skills.forEach((s, v) -> skills.put(s,Integer.valueOf(document.getElementsByTagName(s.name()).item(i).getTextContent().trim())));
        player.setSkills(skills);
    }
}
