# Loads window titles from multiple files.
# Classifies the window based on its title.

class WindowClassifier:

    def __init__(self):
        self.title_sets = {
            'Social': self.load_window_titles('./titles/social.txt'),
            'Utility': self.load_window_titles('./titles/utilities.txt'),
            'Office & Study': self.load_window_titles('./titles/office_study.txt'),
            'Entertainment': self.load_window_titles('./titles/entertainment.txt'),
            'Web Browsing': self.load_window_titles('./titles/web.txt')
        }

    def load_window_titles(self, path: str) -> set:
        with open(path, 'r') as fd:
            titles = fd.read().split('\n')
            return set(titles)

    def classify_window(self, window_title: str) -> dict:
        for label, titles in self.title_sets.items():
            for title in titles:
                if title in window_title:
                    return { 'window': title.title(), 'window_category': label }
                
        return { 'window_category': 'Other' }