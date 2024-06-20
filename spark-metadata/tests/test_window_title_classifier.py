from pytest import fixture
from src.window_title_classifier import WindowTitleClassifier

# Unit tests for the WindowTitleClassifier class.
# The tests examine both the successful and unsuccessful scenarios.
# Author: Antonio Scardace
# Version: 1.0

@fixture
def window_title_classifier():
    return WindowTitleClassifier('')

def test_classify_window_title_entertainment(window_title_classifier, monkeypatch):
    monkeypatch.setattr(window_title_classifier, 'translated_window_title', 'Netflix')
    result = window_title_classifier.classify_window_title()
    assert result == { 'window_title': 'Netflix', 'window_category': 'Entertainment' }

def test_classify_window_title_office_study(window_title_classifier, monkeypatch):
    monkeypatch.setattr(window_title_classifier, 'translated_window_title', 'Slack')
    result = window_title_classifier.classify_window_title()
    assert result == { 'window_title': 'Slack', 'window_category': 'Office & Study' }

def test_classify_window_title_social(window_title_classifier, monkeypatch):
    monkeypatch.setattr(window_title_classifier, 'translated_window_title', 'Whatsapp')
    result = window_title_classifier.classify_window_title()
    assert result == { 'window_title': 'Whatsapp', 'window_category': 'Social' }

def test_classify_window_title_utilities(window_title_classifier, monkeypatch):
    monkeypatch.setattr(window_title_classifier, 'translated_window_title', 'Calculator')
    result = window_title_classifier.classify_window_title()
    assert result == { 'window_title': 'Calculator', 'window_category': 'Utility' }

def test_classify_window_title_web(window_title_classifier, monkeypatch):
    monkeypatch.setattr(window_title_classifier, 'translated_window_title', 'Safari')
    result = window_title_classifier.classify_window_title()
    assert result == { 'window_title': 'Safari', 'window_category': 'Web Browsing' }

def test_classify_window_title_unknown(window_title_classifier, monkeypatch):
    monkeypatch.setattr(window_title_classifier, 'translated_window_title', 'Random Title')
    result = window_title_classifier.classify_window_title()
    assert result == { 'window_title': 'Random Title', 'window_category': 'Unknown' }