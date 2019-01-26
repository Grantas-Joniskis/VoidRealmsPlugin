package me.grantisj.board.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ConfigFile {

    private YamlConfiguration config;
    private File file;

    public static final char PATH_SEPARATOR = '.';

    public ConfigFile(Plugin plugin, String filename) {
        this.file = new File(plugin.getDataFolder(), filename);
    }


    public YamlConfiguration getConfig() {
        return this.config;
    }

    public void load() {
        config = new YamlConfiguration();
        try {
            if (!this.file.exists()) {
                this.file.getParentFile().mkdirs();
                this.writeToFile(this.file.getName(), this.file);
            }

            this.loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return this.config.getConfigurationSection(path);
    }

    public void createSection(String path) {
        this.config.createSection(path);
        this.save();
    }

    public Object getValue(String path) {
        return this.config.get(path);
    }

    public Object getValueOrDefault(String path, Object result) {
        Object object = this.config.get(path);
        return object == null ? result : object;
    }

    public boolean exists(String path) {
        return this.getValue(path) == null;
    }

    public void setValue(String path, Object value) {
        this.config.set(path, value);
        this.save();
    }

    private void loadConfig() throws IOException, InvalidConfigurationException {
        if(this.config == null) {
            throw new NullPointerException("Error Could not load " + file.getAbsolutePath());
        } else {
            this.config.load(this.file);
        }
    }

    public void reload() {
        this.load();
        this.save();
    }

    public void delete() {
        this.file.delete();
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveDefaultConfig() {
        this.config.options().copyDefaults(true);
    }

    private void writeToFile(String source, File destination) {
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = this.getResource(source);
            os = new FileOutputStream(destination);

            byte[] buffer = new byte[1024];
            int i = 0;
            while((i = is.read(buffer)) != -1) {
                os.write(buffer, 0, i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {}
        }
    }

    private InputStream getResource(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        } else {
            try {
                URL url = this.getClass().getClassLoader().getResource(filename);
                if (url.getFile() == null) {
                    return null;
                } else {
                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(false);
                    return connection.getInputStream();
                }
            } catch (IOException var4) {
                return null;
            }
        }
    }

}
