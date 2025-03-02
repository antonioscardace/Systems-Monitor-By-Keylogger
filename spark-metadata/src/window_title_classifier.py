from deep_translator import GoogleTranslator

# Loads window titles from multiple files: one for each window category.
# Classifies the window based on its title.
# Author: Antonio Scardace
# Version: 1.0

class WindowTitleClassifier:

    def __init__(self, window_title: str):
        self.window_title = window_title
        self.translated_window_title = GoogleTranslator().translate(window_title)

        self.title_sets = {
            'Social':         self.__load_titles('titles/social.txt'),
            'Utility':        self.__load_titles('titles/utilities.txt'),
            'Office & Study': self.__load_titles('titles/office_study.txt'),
            'Entertainment':  self.__load_titles('titles/entertainment.txt'),
            'Web Browsing':   self.__load_titles('titles/web.txt')
        }

    def __load_titles(self, file_path: str) -> set:
        with open(file_path, 'r') as f:
            titles = f.read().split('\n')
            return set(titles)
        
    # Classifies the window title based on predefined title sets.
    # If a match is found, it returns a dictionary with the matched window title and its category.
    # If no match is found, it returns the translated window title with the category 'Unknown'.

    def classify_window_title(self) -> dict:
        for label, titles in self.title_sets.items():
            for title in titles:
                if title.lower() in self.translated_window_title.lower():
                    return { 'window_title': title.title(), 'window_category': label }
                
        return { 'window_title': self.translated_window_title, 'window_category': 'Unknown' }