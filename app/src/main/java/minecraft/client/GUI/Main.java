package minecraft.client.GUI;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import minecraft.client.GUI.Background.VersionsVanilla;
import minecraft.client.persistance.FileManager;
import minecraft.client.util.ColorUtils;
import minecraft.client.util.Locations;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.json.JSONObject;

import minecraft.client.GUI.Background.VersionDownloader;
import minecraft.client.GUI.Background.VersionsFabric;
import minecraft.client.GUI.Background.VersionsForge;

/**
 *
 * @author keiner5212
 */
public class Main extends javax.swing.JFrame {

        private String loaderVersionSelected = "";
        private Logger installerLogger;
        private HashMap<String, Object> userJson;
        private HashMap<String, Object> profiles;

        public Main() {
                try {
                        // look and feel
                        UIManager.setLookAndFeel(new HiFiLookAndFeel());

                        // logo
                        ImageIcon icono = new ImageIcon(getClass().getResource("/logo.png"));
                        setIconImage(icono.getImage());

                        // title
                        setTitle("Minecraft Launcher (By keiner5212)");

                } catch (Exception e) {
                        e.printStackTrace();
                }

                initComponents();

                // config
                setLocationRelativeTo(null);
                setResizable(false);
                loaderversionlabel.setEnabled(false);
                loaderversion.setEnabled(false);

                Border rightBorder = BorderFactory.createMatteBorder(0, 0, 0, 2,
                                ColorUtils.hexStringToColor("#161313"));
                instalations_and_name.setBorder(rightBorder);
                Border topBorder = BorderFactory.createMatteBorder(2, 0, 0, 0, ColorUtils.hexStringToColor("#161313"));
                play_download.setBorder(topBorder);

                try {
                        VersionsVanilla versionsThread = new VersionsVanilla(versions,
                                        typeFilter.getSelectedItem().toString());
                        Thread thread = new Thread(versionsThread);
                        thread.start();
                        thread.join();
                        typeFilter.setEnabled(true);
                        versions.setEnabled(true);
                } catch (Exception e) {
                        e.printStackTrace();
                }

                installerLogger = new Logger(loggerscroll, logger, progressbar, progresslabel);

                JSONObject readed = FileManager.loadData(Locations.USER_DATA_LOCATION);
                if (readed != null) {
                        userJson = (HashMap<String, Object>) readed.toMap();
                } else {
                        userJson = new HashMap<>();
                }

                // values
                if (userJson.containsKey("username")) {
                        username.setText(userJson.get("username").toString());
                } else {
                        String userName = System.getProperty("user.name");
                        username.setText(userName);
                }

                if (userJson.containsKey("gamedir")) {
                        gamedir.setText(userJson.get("gamedir").toString());
                        File ProfileDir = new File(gamedir.getText().trim() + "\\" + Locations.PROFILES_LOCATION);
                        if (!ProfileDir.exists()) {
                                try {
                                        HashMap<String, Object> instalations = new HashMap<>();
                                        instalations.put("profiles", new HashMap<>());
                                        FileManager.saveData(
                                                        gamedir.getText().trim() + "\\" + Locations.PROFILES_LOCATION,
                                                        new JSONObject(instalations));
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                        }
                } else {
                        String userName = System.getProperty("user.name");
                        String defaultDirPath = "C:\\Users\\" + userName + "\\AppData\\Roaming\\.minecraft";
                        File ProfileDir = new File(defaultDirPath + "\\" + Locations.PROFILES_LOCATION);
                        if (!ProfileDir.exists()) {
                                try {
                                        HashMap<String, Object> instalations = new HashMap<>();
                                        instalations.put("profiles", new HashMap<>());
                                        FileManager.saveData(defaultDirPath + "\\" + Locations.PROFILES_LOCATION,
                                                        new JSONObject(instalations));
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                        }
                        File defaultDir = new File(defaultDirPath);
                        if (!defaultDir.exists()) {
                                defaultDir.mkdirs();
                        }
                        gamedir.setText(defaultDir.getAbsolutePath());
                }

                JSONObject readedprofiles = FileManager
                                .loadData(gamedir.getText() + "\\" + Locations.PROFILES_LOCATION);
                if (readedprofiles != null) {
                        profiles = (HashMap<String, Object>) readedprofiles.toMap();
                } else {
                        profiles = new HashMap<>();
                }

                jvm.setText("C:\\Program Files\\Java\\jdk-22\\bin\\java.exe");

                if (profiles.containsKey("profiles")) {
                        updateInstalations();
                } else {
                        profiles.put("profiles", new HashMap<>());
                }

                memory.setSelectedItem("4");

        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                instalations_and_name = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                username = new javax.swing.JTextField();
                jScrollPane1 = new javax.swing.JScrollPane();
                instalationList = new javax.swing.JList<>();
                jLabel4 = new javax.swing.JLabel();
                loaderlabel1 = new javax.swing.JLabel();
                gamedir = new javax.swing.JTextField();
                jPanel3 = new javax.swing.JPanel();
                play_download = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                versions = new javax.swing.JComboBox<>();
                loaderlabel = new javax.swing.JLabel();
                Loader = new javax.swing.JComboBox<>();
                typelabel = new javax.swing.JLabel();
                typeFilter = new javax.swing.JComboBox<>();
                loaderversionlabel = new javax.swing.JLabel();
                loaderversion = new javax.swing.JComboBox<>();
                play = new javax.swing.JButton();
                loaderlabel2 = new javax.swing.JLabel();
                memory = new javax.swing.JComboBox<>();
                loaderlabel3 = new javax.swing.JLabel();
                resolution = new javax.swing.JTextField();
                loaderlabel4 = new javax.swing.JLabel();
                instalationname = new javax.swing.JTextField();
                viewfiles = new javax.swing.JButton();
                deleteinstalation = new javax.swing.JButton();
                jLabel3 = new javax.swing.JLabel();
                jvm = new javax.swing.JTextField();
                javaChange = new javax.swing.JButton();
                progresslabel = new javax.swing.JLabel();
                progressbar = new javax.swing.JProgressBar();
                loggerscroll = new javax.swing.JScrollPane();
                logger = new javax.swing.JTextArea();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

                instalations_and_name.setMaximumSize(new java.awt.Dimension(300, 32767));
                instalations_and_name.setMinimumSize(new java.awt.Dimension(300, 0));

                jLabel2.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                jLabel2.setText("Username:");

                username.setFont(jLabel1.getFont());

                instalationList.setFont(jLabel1.getFont());
                instalationList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                        public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                                instalationListValueChanged(evt);
                        }
                });
                jScrollPane1.setViewportView(instalationList);

                jLabel4.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                jLabel4.setText("Instalations:");

                loaderlabel1.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                loaderlabel1.setText("Launcher directory:");

                gamedir.setFont(jLabel1.getFont());

                javax.swing.GroupLayout instalations_and_nameLayout = new javax.swing.GroupLayout(
                                instalations_and_name);
                instalations_and_name.setLayout(instalations_and_nameLayout);
                instalations_and_nameLayout.setHorizontalGroup(
                                instalations_and_nameLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(instalations_and_nameLayout.createSequentialGroup()
                                                                .addGap(25, 25, 25)
                                                                .addGroup(instalations_and_nameLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(jLabel4)
                                                                                .addComponent(jLabel2)
                                                                                .addComponent(loaderlabel1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                133,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jScrollPane1)
                                                                                .addComponent(username)
                                                                                .addComponent(gamedir))
                                                                .addGap(25, 25, 25)));
                instalations_and_nameLayout.setVerticalGroup(
                                instalations_and_nameLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(instalations_and_nameLayout.createSequentialGroup()
                                                                .addGap(36, 36, 36)
                                                                .addComponent(jLabel4)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                308,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(31, 31, 31)
                                                                .addComponent(jLabel2)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(username,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(loaderlabel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                23,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(gamedir,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(37, Short.MAX_VALUE)));

                jPanel1.add(instalations_and_name);

                jLabel1.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                jLabel1.setText("Select the game version:");

                versions.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                versions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Loading" }));
                versions.setEnabled(false);
                versions.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                versionsActionPerformed(evt);
                        }
                });

                loaderlabel.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                loaderlabel.setText("Select loader:");

                Loader.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                Loader.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vanilla", "Forge", "Fabric" }));
                Loader.setEnabled(false);
                Loader.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                LoaderActionPerformed(evt);
                        }
                });

                typelabel.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                typelabel.setText("Type filter:");

                typeFilter.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                typeFilter.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "Any", "Release", "Snapshot", "Other" }));
                typeFilter.setEnabled(false);
                typeFilter.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                typeFilterActionPerformed(evt);
                        }
                });

                loaderversionlabel.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                loaderversionlabel.setText("Loader Version:");

                loaderversion.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                loaderversion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Waiting" }));
                loaderversion.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                loaderversionActionPerformed(evt);
                        }
                });

                play.setBackground(new java.awt.Color(102, 255, 102));
                play.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 18)); // NOI18N
                play.setForeground(new java.awt.Color(0, 0, 0));
                play.setText("Play");
                play.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                playActionPerformed(evt);
                        }
                });

                loaderlabel2.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                loaderlabel2.setText("Memory (GB):");

                memory.setFont(jLabel1.getFont());
                memory.setModel(new javax.swing.DefaultComboBoxModel<>(
                                new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

                loaderlabel3.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                loaderlabel3.setText("Resolution:");

                resolution.setText("1366x768");

                loaderlabel4.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                loaderlabel4.setText("Instalation name:");

                instalationname.setFont(jLabel1.getFont());

                viewfiles.setText("View Files");
                viewfiles.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                viewfilesActionPerformed(evt);
                        }
                });

                deleteinstalation.setFont(jLabel1.getFont());
                deleteinstalation.setText("Delete instalation");
                deleteinstalation.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                deleteinstalationActionPerformed(evt);
                        }
                });

                jLabel3.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
                jLabel3.setText("Java Path:");

                jvm.setFont(jLabel1.getFont());

                javaChange.setText("Change");
                javaChange.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                javaChangeActionPerformed(evt);
                        }
                });

                progressbar.setBackground(new java.awt.Color(102, 102, 102));

                javax.swing.GroupLayout play_downloadLayout = new javax.swing.GroupLayout(play_download);
                play_download.setLayout(play_downloadLayout);
                play_downloadLayout.setHorizontalGroup(
                                play_downloadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(play_downloadLayout.createSequentialGroup()
                                                                .addGap(45, 45, 45)
                                                                .addGroup(play_downloadLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addGroup(play_downloadLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(play_downloadLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(deleteinstalation)
                                                                                                                .addGroup(play_downloadLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(loaderlabel4,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                109,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                .addComponent(instalationname,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                248,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addComponent(viewfiles))
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                play_downloadLayout
                                                                                                                                                .createSequentialGroup()
                                                                                                                                                .addComponent(loaderlabel2,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                85,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                                                .addComponent(memory,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                97,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addGap(45, 45, 45)
                                                                                                                                                .addComponent(loaderlabel3,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                85,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                                22,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(resolution,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                141,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                play_downloadLayout
                                                                                                                                                .createSequentialGroup()
                                                                                                                                                .addGroup(play_downloadLayout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                false)
                                                                                                                                                                .addComponent(jLabel3,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                .addComponent(loaderlabel,
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                .addComponent(Loader,
                                                                                                                                                                                0,
                                                                                                                                                                                99,
                                                                                                                                                                                Short.MAX_VALUE))
                                                                                                                                                .addGroup(play_downloadLayout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                                false)
                                                                                                                                                                .addGroup(play_downloadLayout
                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                .addGap(45, 45, 45)
                                                                                                                                                                                .addGroup(play_downloadLayout
                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                                .addComponent(jLabel1)
                                                                                                                                                                                                .addComponent(versions,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                168,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                                                                .addGap(45, 45, 45)
                                                                                                                                                                                .addGroup(play_downloadLayout
                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                                                                                .addGroup(play_downloadLayout
                                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                                .addComponent(typelabel)
                                                                                                                                                                                                                .addGap(71, 71, 71))
                                                                                                                                                                                                .addComponent(typeFilter,
                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                130,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                play_downloadLayout
                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                .addComponent(jvm,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                                272,
                                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                                                .addComponent(javaChange)
                                                                                                                                                                                                .addGap(10, 10, 10)))))
                                                                                                .addGap(45, 45, 45)
                                                                                                .addGroup(play_downloadLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(loaderversionlabel)
                                                                                                                .addComponent(loaderversion,
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                0,
                                                                                                                                122,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(play,
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)))
                                                                                .addGroup(play_downloadLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(progresslabel,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                248,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(progressbar,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)))
                                                                .addGap(45, 45, 45)));
                play_downloadLayout.setVerticalGroup(
                                play_downloadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                play_downloadLayout.createSequentialGroup()
                                                                                .addGroup(play_downloadLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(progresslabel,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                26,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGroup(play_downloadLayout
                                                                                                                .createSequentialGroup()
                                                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                                                .addComponent(progressbar,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                19,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGap(9, 9, 9)
                                                                                .addGroup(play_downloadLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(jLabel1)
                                                                                                .addComponent(loaderlabel)
                                                                                                .addComponent(typelabel)
                                                                                                .addComponent(loaderversionlabel))
                                                                                .addPreferredGap(
                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addGroup(play_downloadLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(versions,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(Loader,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(typeFilter,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(loaderversion,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGap(18, 18, 18)
                                                                                .addGroup(play_downloadLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(jLabel3)
                                                                                                .addComponent(jvm,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(javaChange))
                                                                                .addGap(18, 18, 18)
                                                                                .addGroup(play_downloadLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(loaderlabel2,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                23,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(memory,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(loaderlabel3,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                23,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(resolution,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(play_downloadLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addGroup(play_downloadLayout
                                                                                                                .createSequentialGroup()
                                                                                                                .addGap(18, 18, 18)
                                                                                                                .addGroup(play_downloadLayout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                .addComponent(loaderlabel4,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                23,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addComponent(instalationname,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addComponent(viewfiles))
                                                                                                                .addGap(18, 18, 18)
                                                                                                                .addComponent(deleteinstalation))
                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                play_downloadLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addComponent(play,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                49,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGap(20, 20, 20)));

                logger.setEditable(false);
                logger.setColumns(20);
                logger.setRows(5);
                logger.setText("logs:\n");
                loggerscroll.setViewportView(logger);

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(play_download, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(loggerscroll));
                jPanel3Layout.setVerticalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(loggerscroll,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                255,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(play_download,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));

                jPanel1.add(jPanel3);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void javaChangeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_javaChangeActionPerformed
                String defaultDirPath = "C:\\Program Files\\Java\\jdk-22\\bin\\";
                File defaultDir = new File(defaultDirPath);
                if (!defaultDir.exists()) {
                        defaultDir.mkdirs();
                }
                JFileChooser chooser = new JFileChooser(defaultDir);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File selectedDirectory = chooser.getSelectedFile();
                        File jvmfile = new File(selectedDirectory, "java.exe");
                        if (!jvmfile.exists()) {
                                JOptionPane.showMessageDialog(null, "java.exe not found", "Error",
                                                JOptionPane.ERROR_MESSAGE);
                        }
                        jvm.setText(jvmfile.getAbsolutePath());
                }
        }// GEN-LAST:event_javaChangeActionPerformed

        @SuppressWarnings("unchecked")
        private void updateInstalations() {
                HashMap<String, HashMap<String, Object>> instalations = (HashMap<String, HashMap<String, Object>>) profiles
                                .get("profiles");
                DefaultListModel<String> model = new DefaultListModel<>();
                for (String key : instalations.keySet()) {
                        model.addElement(instalations.get(key).get("name").toString());
                }
                instalationList.setModel(model);
        }

        @SuppressWarnings("unchecked")
        private void deleteinstalationActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteinstalationActionPerformed
                HashMap<String, HashMap<String, Object>> instalations = (HashMap<String, HashMap<String, Object>>) profiles
                                .get("profiles");
                Object remove = instalations.remove(instalationList.getSelectedValue());
                profiles.put("profiles", instalations);
                if (remove != null) {
                        FileManager.saveData(gamedir.getText().trim() + "\\" + Locations.PROFILES_LOCATION,
                                        new JSONObject(profiles));
                        updateInstalations();
                }

        }// GEN-LAST:event_deleteinstalationActionPerformed

        @SuppressWarnings("unchecked")
        private void instalationListValueChanged(javax.swing.event.ListSelectionEvent evt) {// GEN-FIRST:event_instalationListValueChanged
                if (!evt.getValueIsAdjusting()) {
                        HashMap<String, HashMap<String, Object>> instalations = (HashMap<String, HashMap<String, Object>>) profiles
                                        .get("profiles");
                        if (instalations.get(instalationList.getSelectedValue()) != null) {
                                instalationname.setText(instalationList.getSelectedValue());
                                HashMap<String, Object> selectedInstalation = instalations
                                                .get(instalationList.getSelectedValue());
                                jvm.setText(selectedInstalation.get("jvm").toString());
                                Loader.setSelectedItem(selectedInstalation.get("loader").toString());
                                typeFilter.setSelectedItem(selectedInstalation.get("type").toString());
                                versions.setSelectedItem(selectedInstalation.get("lastVersionId").toString());
                                memory.setSelectedItem(selectedInstalation.get("memory").toString());
                                resolution.setText(selectedInstalation.get("resolution").toString());
                        }

                }
        }// GEN-LAST:event_instalationListValueChanged

        private void viewfilesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_viewfilesActionPerformed
                ProcessBuilder processBuilder = new ProcessBuilder();
                ArrayList<String> command = new ArrayList<>();
                command.add("explorer");
                command.add(gamedir.getText().trim());
                processBuilder.command(command);
                try {
                        processBuilder.start();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }// GEN-LAST:event_viewfilesActionPerformed

        private void typeFilterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_typeFilterActionPerformed
                VersionsVanilla versionsThread = new VersionsVanilla(versions, typeFilter.getSelectedItem().toString());
                Thread thread = new Thread(versionsThread);
                thread.start();
                try {
                        thread.join();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                Loader.setEnabled(typeFilter.getSelectedItem().toString().equals("Release"));
        }// GEN-LAST:event_typeFilterActionPerformed

        private void LoaderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_LoaderActionPerformed
                Thread thread;
                switch (Loader.getSelectedItem().toString()) {
                        case "Fabric":
                                loaderversionlabel.setEnabled(true);
                                loaderversion.setEnabled(true);
                                typeFilter.setEnabled(false);
                                VersionsFabric versionsThreadFabric = new VersionsFabric(loaderversion);
                                thread = new Thread(versionsThreadFabric);
                                thread.start();

                                break;

                        case "Forge":
                                loaderversionlabel.setEnabled(true);
                                loaderversion.setEnabled(true);
                                typeFilter.setEnabled(false);
                                VersionsForge versionsThreadForge = new VersionsForge(loaderversion,
                                                versions.getSelectedItem().toString());
                                thread = new Thread(versionsThreadForge);
                                thread.start();

                                break;

                        case "Vanilla":
                                loaderversionlabel.setEnabled(false);
                                loaderversion.setEnabled(false);
                                typeFilter.setSelectedItem("Any");
                                typeFilter.setEnabled(true);

                                break;

                        default:
                                break;
                }
        }// GEN-LAST:event_LoaderActionPerformed

        private void loaderversionActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_loaderversionActionPerformed
                if (loaderversion.getSelectedItem() == null) {
                        return;
                }
                loaderVersionSelected = loaderversion.getSelectedItem().toString();
        }// GEN-LAST:event_loaderversionActionPerformed

        private void versionsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_versionsActionPerformed
                if (Loader.getSelectedItem().toString().equalsIgnoreCase("Forge")) {
                        if (versions.getSelectedItem() == null) {
                                return;
                        }
                        VersionsForge versionsThreadForge = new VersionsForge(loaderversion,
                                        versions.getSelectedItem().toString());
                        Thread thread = new Thread(versionsThreadForge);
                        thread.start();
                }
        }// GEN-LAST:event_versionsActionPerformed

        @SuppressWarnings("unchecked")
        private void playActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_playActionPerformed
                if (username.getText().isEmpty() || jvm.getText().isEmpty() || gamedir.getText().isEmpty()
                                || resolution.getText().isEmpty() || instalationname.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "All fields are required", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (Loader.getSelectedItem().toString().equalsIgnoreCase("Vanilla")) {
                        userJson.put("username", username.getText());
                        userJson.put("gamedir", gamedir.getText());
                        HashMap<String, Object> instalationData = new HashMap<>();
                        instalationData.put("jvm", jvm.getText());
                        instalationData.put("type", typeFilter.getSelectedItem().toString());
                        instalationData.put("loader", Loader.getSelectedItem().toString());
                        instalationData.put("loaderVersion", loaderVersionSelected);
                        instalationData.put("lastVersionId",
                                        versions.getSelectedItem().toString());
                        instalationData.put("memory", memory.getSelectedItem().toString());
                        instalationData.put("resolution", resolution.getText());
                        instalationData.put("name", instalationname.getText());

                        HashMap<String, Object> instalatedprofiles = (HashMap<String, Object>) profiles
                                        .get("profiles");
                        instalatedprofiles.put(instalationname.getText(), instalationData);

                        FileManager.saveData(Locations.USER_DATA_LOCATION,
                                        new JSONObject(userJson));
                        FileManager.saveData(gamedir.getText().trim() + "\\" + Locations.PROFILES_LOCATION,
                                        new JSONObject(profiles));
                }

                VersionDownloader versionDownloader = new VersionDownloader(Loader.getSelectedItem().toString(),
                                versions.getSelectedItem().toString(), loaderVersionSelected, gamedir.getText().trim(),
                                installerLogger,
                                Integer.parseInt(memory.getSelectedItem().toString()) * 1024, username.getText().trim(),
                                Integer.parseInt(resolution.getText().trim().split("x")[0]),
                                Integer.parseInt(resolution.getText().trim().split("x")[1]),
                                jvm.getText().trim());
                Thread thread = new Thread(versionDownloader);
                thread.start();
        }// GEN-LAST:event_playActionPerformed

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JComboBox<String> Loader;
        private javax.swing.JButton deleteinstalation;
        private javax.swing.JTextField gamedir;
        private javax.swing.JList<String> instalationList;
        private javax.swing.JTextField instalationname;
        private javax.swing.JPanel instalations_and_name;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JButton javaChange;
        private javax.swing.JTextField jvm;
        private javax.swing.JLabel loaderlabel;
        private javax.swing.JLabel loaderlabel1;
        private javax.swing.JLabel loaderlabel2;
        private javax.swing.JLabel loaderlabel3;
        private javax.swing.JLabel loaderlabel4;
        private javax.swing.JComboBox<String> loaderversion;
        private javax.swing.JLabel loaderversionlabel;
        private javax.swing.JTextArea logger;
        private javax.swing.JScrollPane loggerscroll;
        private javax.swing.JComboBox<String> memory;
        private javax.swing.JButton play;
        private javax.swing.JPanel play_download;
        private javax.swing.JProgressBar progressbar;
        private javax.swing.JLabel progresslabel;
        private javax.swing.JTextField resolution;
        private javax.swing.JComboBox<String> typeFilter;
        private javax.swing.JLabel typelabel;
        private javax.swing.JTextField username;
        private javax.swing.JComboBox<String> versions;
        private javax.swing.JButton viewfiles;
        // End of variables declaration//GEN-END:variables
}
