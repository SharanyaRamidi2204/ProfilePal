import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.net.*;

public class CombinedApp {
    private static final Map<String, String> userCredentials = new HashMap<>();
    private static final Map<String, Map<String, String>> resumeContent = new HashMap<>();
    private static String photoPath;

    static {
        // Pre-prepared resume content for different engineering branches
        Map<String, String> mechanicalEngineeringContent = new HashMap<>();
        mechanicalEngineeringContent.put("Education", "Bachelor of Science in Mechanical Engineering, XYZ University");
        mechanicalEngineeringContent.put("Skills", "Proficient in CAD, SolidWorks, Thermodynamics, Fluid Mechanics");
        mechanicalEngineeringContent.put("Experience", "Internship at Manufacturing Company, Mechanical Engineer (2022 - 2023)");
        mechanicalEngineeringContent.put("Projects", "Designed and fabricated a solar-powered vehicle");
        mechanicalEngineeringContent.put("Achievements", "Received the Best Project Award in the university");
        mechanicalEngineeringContent.put("PersonalInfo", "John is a dedicated individual with a passion for mechanical engineering. During his internship, he demonstrated exceptional problem-solving skills and a keen eye for detail. His ability to work effectively in a team and his strong work ethic were instrumental in the success of our projects. He actively contributed to the design and fabrication of innovative solutions, showcasing his technical prowess.");

        Map<String, String> civilEngineeringContent = new HashMap<>();
        civilEngineeringContent.put("Education", "Bachelor of Science in Civil Engineering, XYZ University");
        civilEngineeringContent.put("Skills", "Proficient in AutoCAD, Structural Analysis, Project Management");
        civilEngineeringContent.put("Experience", "Internship at Construction Company, Civil Engineer (2022 - 2023)");
        civilEngineeringContent.put("Projects", "Designed and supervised the construction of a pedestrian bridge");
        civilEngineeringContent.put("Achievements", "Received the Best Structural Design Award");
        civilEngineeringContent.put("PersonalInfo", "A highly motivated civil engineer with a passion for designing and building sustainable structures. During her internship, she showcased her project management skills and her ability to work under pressure. Her innovative design solutions contributed significantly to the successful completion of projects. Jane's leadership qualities and commitment to sustainability make her an invaluable asset to any engineering team.");

        Map<String, String> electricalEngineeringContent = new HashMap<>();
        electricalEngineeringContent.put("Education", "Bachelor of Science in Electrical Engineering, XYZ University");
        electricalEngineeringContent.put("Skills", "Proficient in Circuit Design, Power Systems, MATLAB, Embedded Systems");
        electricalEngineeringContent.put("Experience", "Internship at Power Company, Electrical Engineer (2022 - 2023)");
        electricalEngineeringContent.put("Projects", "Developed an energy-efficient power distribution system");
        electricalEngineeringContent.put("Achievements", "Received the Best Innovation Award");
        electricalEngineeringContent.put("PersonalInfo", "A skilled electrical engineer with a strong background in power systems and circuit design. During her internship, she demonstrated excellent analytical skills and a commitment to sustainability. Her innovative approach to power distribution helped in reducing energy losses significantly. Emily's technical expertise and proactive attitude make her a standout professional in her field.");

        Map<String, String> chemicalEngineeringContent = new HashMap<>();
        chemicalEngineeringContent.put("Education", "Bachelor of Science in Chemical Engineering, XYZ University");
        chemicalEngineeringContent.put("Skills", "Proficient in Process Design, Chemical Reactions, HYSYS, Aspen Plus");
        chemicalEngineeringContent.put("Experience", "Internship at Chemical Plant, Chemical Engineer (2022 - 2023)");
        chemicalEngineeringContent.put("Projects", "Optimized the chemical production process to increase yield");
        chemicalEngineeringContent.put("Achievements", "Received the Best Process Optimization Award");
        chemicalEngineeringContent.put("PersonalInfo", "A dedicated chemical engineer with a passion for optimizing chemical processes. During his internship, he showcased his expertise in process design and his ability to work collaboratively with a team. His contributions led to significant improvements in production efficiency and product quality. Michael's innovative mindset and collaborative spirit make him a valuable addition to any team.");

        Map<String, String> computerScienceEngineeringContent = new HashMap<>();
        computerScienceEngineeringContent.put("Education", "Bachelor of Science in Computer Science Engineering, XYZ University");
        computerScienceEngineeringContent.put("Skills", "Proficient in Java, Python, Algorithms, Data Structures, Web Development");
        computerScienceEngineeringContent.put("Experience", "Internship at Software Company, Software Developer (2022 - 2023)");
        computerScienceEngineeringContent.put("Projects", "Developed a web-based project management tool");
        computerScienceEngineeringContent.put("Achievements", "Received the Best Software Development Award");
        computerScienceEngineeringContent.put("PersonalInfo", "A talented computer science engineer with a passion for software development and web technologies. During her internship, she demonstrated exceptional programming skills and a keen eye for detail. Her ability to develop user-friendly and efficient software solutions made her a valuable asset to the team. Alice's creativity and technical proficiency position her well for a successful career in software engineering.");

        Map<String, String> informationTechnologyContent = new HashMap<>();
        informationTechnologyContent.put("Education", "Bachelor of Science in Information Technology, XYZ University");
        informationTechnologyContent.put("Skills", "Proficient in Network Administration, Cybersecurity, Database Management");
        informationTechnologyContent.put("Experience", "Internship at IT Company, IT Specialist (2022 - 2023)");
        informationTechnologyContent.put("Projects", "Implemented a secure network infrastructure for the company");
        informationTechnologyContent.put("Achievements", "Received the Best Cybersecurity Implementation Award");
        informationTechnologyContent.put("PersonalInfo", "A dedicated IT professional with a strong background in network administration and cybersecurity. During his internship, he demonstrated excellent problem-solving skills and a commitment to securing IT infrastructure. His proactive approach to cybersecurity helped in safeguarding the company's data and network. Bob's technical expertise and dedication to IT security make him an indispensable member of any IT team.");

        Map<String, String> aerospaceEngineeringContent = new HashMap<>();
        aerospaceEngineeringContent.put("Education", "Bachelor of Science in Aerospace Engineering, XYZ University");
        aerospaceEngineeringContent.put("Skills", "Proficient in Aerodynamics, Propulsion Systems, MATLAB, CAD");
        aerospaceEngineeringContent.put("Experience", "Internship at Aerospace Company, Aerospace Engineer (2022 - 2023)");
        aerospaceEngineeringContent.put("Projects", "Designed a high-efficiency wing for a commercial aircraft");
        aerospaceEngineeringContent.put("Achievements", "Received the Best Aerospace Design Award");
        aerospaceEngineeringContent.put("PersonalInfo", "A passionate aerospace engineer with a strong background in aerodynamics and propulsion systems. During his internship, he demonstrated excellent analytical skills and a commitment to innovation. His contributions to the design and optimization of aircraft components significantly improved performance and efficiency. David's dedication to advancing aerospace technology is evident in his outstanding work.");

        Map<String, String> biomedicalEngineeringContent = new HashMap<>();
        biomedicalEngineeringContent.put("Education", "Bachelor of Science in Biomedical Engineering, XYZ University");
        biomedicalEngineeringContent.put("Skills", "Proficient in Biomedical Devices, Signal Processing, Biomechanics");
        biomedicalEngineeringContent.put("Experience", "Internship at Medical Device Company, Biomedical Engineer (2022 - 2023)");
        biomedicalEngineeringContent.put("Projects", "Developed a wearable health monitoring device");
        biomedicalEngineeringContent.put("Achievements", "Received the Best Innovation in Biomedical Engineering Award");
        biomedicalEngineeringContent.put("PersonalInfo", "A dedicated biomedical engineer with a passion for developing innovative medical devices. During her internship, she showcased her expertise in signal processing and her ability to work effectively in a multidisciplinary team. Her contributions to the development of a wearable health monitoring device have the potential to significantly impact patient care and monitoring. Eve's commitment to improving healthcare through technology is truly commendable.");

        resumeContent.put("Mechanical Engineering", mechanicalEngineeringContent);
        resumeContent.put("Civil Engineering", civilEngineeringContent);
        resumeContent.put("Electrical Engineering", electricalEngineeringContent);
        resumeContent.put("Chemical Engineering", chemicalEngineeringContent);
        resumeContent.put("Computer Science Engineering", computerScienceEngineeringContent);
        resumeContent.put("Information Technology", informationTechnologyContent);
        resumeContent.put("Aerospace Engineering", aerospaceEngineeringContent);
        resumeContent.put("Biomedical Engineering", biomedicalEngineeringContent);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CombinedApp::createAndShowLoginGUI);
    }

    private static void createAndShowLoginGUI() {
        JFrame frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(195, 195, 229)); // Set background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding

        JLabel welcomeLabel = new JLabel("Welcome To ProfilePal-Students Best companion");
        welcomeLabel.setFont(new Font("DialogInput", Font.BOLD, 25));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(welcomeLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx++;
        JTextField usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx++;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                openMainMenu();
                frame.dispose(); // Close the login frame
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(loginButton, gbc);

        gbc.gridy++;
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (!userCredentials.containsKey(username)) {
                userCredentials.put(username, password);
                JOptionPane.showMessageDialog(frame, "Sign up successful! You can now sign in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Username already exists. Please choose a different one.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(signUpButton, gbc);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }


    private static void openMainMenu() {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBackground(Color.MAGENTA); // Set background color

        JButton resumeBuilderButton = new JButton("Resume Builder");
        resumeBuilderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openResumeBuilder(frame);
            }
        });
        panel.add(resumeBuilderButton);

        JButton toDoListButton = new JButton("To-Do List");
        toDoListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openToDoListApp();
            }
        });
        panel.add(toDoListButton);
panel.setBackground(new Color(189,174,198)); // Light yellow background
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private static void openResumeBuilder(JFrame previousFrame) {
    JFrame frame = new JFrame("College Resume Builder");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panel = new JPanel(new GridLayout(11, 2));
    panel.setBackground(new Color(204,204,255)); // Set background color

    JButton backButton = new JButton("Back");
    backButton.setBackground(Color.GRAY);
    backButton.setForeground(Color.WHITE);
    backButton.addActionListener(e -> {
        previousFrame.setVisible(true);
        frame.dispose();
    });
    panel.add(backButton);
    panel.add(new JLabel());  // Placeholder for grid layout

    JTextField nameField = new JTextField();
    JTextField hobbiesField = new JTextField();
    JTextField languagesField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField phoneField = new JTextField();
    String[] departments = {"Mechanical Engineering", "Civil Engineering", "Electrical Engineering", "Chemical Engineering", "Computer Science Engineering", "Information Technology"};
    JComboBox<String> departmentComboBox = new JComboBox<>(departments);
    JTextArea experienceTextArea = new JTextArea();
    experienceTextArea.setLineWrap(true);
    JLabel photoLabel = new JLabel("No photo selected");

    JLabel nameLabel = new JLabel("Name:");
    nameLabel.setForeground(Color.BLACK);
    panel.add(nameLabel);
    panel.add(nameField);

    JLabel hobbiesLabel = new JLabel("Hobbies (comma-separated):");
    hobbiesLabel.setForeground(Color.BLACK);
    panel.add(hobbiesLabel);
    panel.add(hobbiesField);

    JLabel languagesLabel = new JLabel("Languages known (comma-separated):");
    languagesLabel.setForeground(Color.BLACK);
    panel.add(languagesLabel);
    panel.add(languagesField);

    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setForeground(Color.BLACK);
    panel.add(emailLabel);
    panel.add(emailField);

    JLabel phoneLabel = new JLabel("Phone:");
    phoneLabel.setForeground(Color.BLACK);
    panel.add(phoneLabel);
    panel.add(phoneField);

    JLabel departmentLabel = new JLabel("Department:");
    departmentLabel.setForeground(Color.BLACK);
    panel.add(departmentLabel);
    panel.add(departmentComboBox);

    JLabel experienceLabel = new JLabel("Internship/Work Experience:");
    experienceLabel.setForeground(Color.BLACK);
    panel.add(experienceLabel);
    JScrollPane experienceScrollPane = new JScrollPane(experienceTextArea);
    panel.add(experienceScrollPane);

    JButton selectPhotoButton = new JButton("Select Photo");
    selectPhotoButton.setBackground(new Color(156,138,165));
    selectPhotoButton.setForeground(Color.BLACK);
    selectPhotoButton.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            photoPath = selectedFile.getAbsolutePath();
            photoLabel.setText("Photo selected: " + selectedFile.getName());
        }
    });
    panel.add(selectPhotoButton);
    photoLabel.setForeground(Color.BLACK);
    panel.add(photoLabel);

    JButton generateButton = new JButton("Generate Resume");
    generateButton.setBackground(new Color(255,255,255));
    generateButton.setForeground(new Color(115,44,123));
    generateButton.addActionListener(e -> {
        String name = nameField.getText();
        String[] hobbies = hobbiesField.getText().split(", ");
        String[] languages = languagesField.getText().split(", ");
        String email = emailField.getText();
        String phone = phoneField.getText();
        String department = (String) departmentComboBox.getSelectedItem();
        String experience = experienceTextArea.getText();

        String resume = generateResume(name, hobbies, languages, email, phone, department) + "\n\n" +
                "Work/Internship Experience:\n" + experience;
        saveResume(name, department, resume);
        saveResumeAsHTML(name, department, resume, email, phone, hobbies, languages, photoPath);
    });
    panel.add(generateButton);

    frame.getContentPane().add(panel);
    frame.pack();
    frame.setVisible(true);
    previousFrame.setVisible(false);  // Hide the previous frame
}


    private static void openToDoListApp() {
        JFrame frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ToDoListApp toDoListApp = new ToDoListApp(frame); // Pass the main menu frame to the ToDoListApp constructor

        frame.getContentPane().add(toDoListApp);
        frame.pack();
        frame.setVisible(true);
    }

    private static String generateResume(String name, String[] hobbies, String[] languages, String email, String phone, String department) {
        StringBuilder resume = new StringBuilder();
        resume.append("Name: ").append(name).append("\n\n");
        resume.append("Department: ").append(department).append("\n\n");
        resume.append("Email: ").append(email).append("\n\n");
        resume.append("Phone: ").append(phone).append("\n\n");
        resume.append("Hobbies: ").append(String.join(", ", hobbies)).append("\n\n");
        resume.append("Languages known: ").append(String.join(", ", languages)).append("\n\n");
        resume.append("--------------------------------\n\n");
        Map<String, String> departmentContent = resumeContent.get(department);
        if (departmentContent != null) {
            for (Map.Entry<String, String> entry : departmentContent.entrySet()) {
                if (entry.getKey().equals("PersonalInfo")) {
                    resume.append("Personal Information:\n").append(entry.getValue()).append("\n\n");
                } else {
                    resume.append(entry.getKey()).append(":\n").append(entry.getValue()).append("\n\n");
                }
            }
        }
        return resume.toString();
    }

    private static void saveResume(String name, String department, String resume) {
        String fileName = name + "_" + department.replaceAll("\\s", "") + "_resume.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(resume);
            System.out.println("Resume saved as " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   private static void saveResumeAsHTML(String name, String department, String resume,
                                     String email, String phone, String[] hobbies, String[] languages, String photoPath) {
    String fileName = name + "_" + department.replaceAll("\\s", "") + "_resume.html";
    try (FileWriter writer = new FileWriter(fileName)) {
        writer.write("<html><body style='background-color: #f4e3ff'>"); // Light purple background color

        writer.write("<h1 style='color: #6d2c7b'>Resume</h1>"); // Heading color
        writer.write("<p><b>Name:</b> " + name + "</p>");
        if (photoPath != null) {
            String encodedImage = encodeImageToBase64(photoPath);
            writer.write("<p><b>Photo:</b><br><img src='data:image/png;base64," + encodedImage + "' alt='User Photo' width='100' height='100'></p>");
        }
        writer.write("<p><b>Department:</b> " + department + "</p>");
        writer.write("<p><b>Email:</b> " + email + "</p>");
        writer.write("<p><b>Phone:</b> " + phone + "</p>");
        writer.write("<p><b>Hobbies:</b> " + String.join(", ", hobbies) + "</p>");
        writer.write("<p><b>Languages known:</b> " + String.join(", ", languages) + "</p>");
        writer.write("<hr>");
        writer.write(resume.replace("\n", "<br>"));

        writer.write("</body></html>");
        writer.flush();
        System.out.println("Resume saved as " + fileName);

        // Open the HTML file in the default web browser
        Desktop.getDesktop().browse(new URI("file://" + new File(fileName).getAbsolutePath()));
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    private static String encodeImageToBase64(String imagePath) {
        try {
            byte[] imageBytes = Files.readAllBytes(new File(imagePath).toPath());
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

class ToDoListApp extends JPanel implements ActionListener {
    private DefaultListModel<String> toDoListModel;
    private JList<String> toDoList;
    private JTextField newItemField;
    private JButton addButton;
    private JButton removeButton;
    private JButton backButton;

    public ToDoListApp(JFrame previousFrame) {
        setLayout(new BorderLayout());
        setBackground(new Color(189,174,198)); // Set background color

        toDoListModel = new DefaultListModel<>();
        toDoList = new JList<>(toDoListModel);
        toDoList.setCellRenderer(new CustomListCellRenderer()); // Use custom renderer
        add(new JScrollPane(toDoList), BorderLayout.CENTER);

        newItemField = new JTextField();
        newItemField.setBackground(new Color(189,174,198));
        newItemField.setForeground(new Color(66,28,82));
        add(newItemField, BorderLayout.NORTH);

        addButton = new JButton("Add");
        addButton.setBackground(new Color(66,28,82));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(this);
        add(addButton, BorderLayout.WEST);

        removeButton = new JButton("Remove");
        removeButton.setBackground(new Color(66,28,82));
        removeButton.setForeground(Color.WHITE);
        removeButton.addActionListener(this);
        add(removeButton, BorderLayout.EAST);

        backButton = new JButton("Back");
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            previousFrame.setVisible(true);
            ((JFrame) SwingUtilities.getRoot(this)).dispose();
        });
        add(backButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String newItem = newItemField.getText();
            if (!newItem.isEmpty()) {
                toDoListModel.addElement(newItem);
                newItemField.setText("");
            }
        } else if (e.getSource() == removeButton) {
            int selectedIndex = toDoList.getSelectedIndex();
            if (selectedIndex != -1) {
                toDoListModel.remove(selectedIndex);
            }
        }
    }

    private static class CustomListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (isSelected) {
                label.setBackground(new Color(255, 215, 0)); // Highlight selected item
                label.setForeground(Color.BLACK);
            } else {
                label.setBackground(new Color(224, 255, 255)); // Set background color for items
                label.setForeground(Color.BLACK);
            }
            return label;
        }
    }
}