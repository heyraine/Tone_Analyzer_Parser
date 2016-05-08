#!/usr/bin/python

import json
import sys
from watson_developer_cloud import ToneAnalyzerV3Beta


def analyzeTone(text):
	emotion = {}
	social = {}
	for ch in ['[', ']','{', '}', '(', ')', " ", '\n', '\"']:
		if ch in text:
			text = text.replace(ch, "")

	text_split = text.split(',')
	for x in range(len(text_split)):
		if "tone_name" in text_split[x] and (x + 1) < len(text_split):
			if "tones" in text_split[x]:
				emote = text_split[x].split(":")[2]
			else:
				emote = text_split[x].split(":")[1]
			score = float(text_split[x + 1].split(":")[1])
			emotion_string = ["Anger", "Sadness", "Fear", "Disgust", "Joy"]
			social_string = ["Agreeableness", "Openness", "Tentative", "Conscientiousness", "Confident"] 
			if emote in emotion_string and emote in emotion:
				emotion[emote] += score
			elif emote in emotion_string:
				emotion[emote] = score
			elif emote in social_string and emote in social:
				social[emote] += score
			elif emote in social_string:
				social[emote] = score

	max_emotion = 0.00
	max_emotion_index = ""
	for k in emotion.keys():
		if emotion[k] > max_emotion:
			max_emotion = emotion[k]
			max_emotion_index = k
	
	max_social = 0.00
	max_social_index = ""
	for j in social.keys():
		if social[j] > max_social:
			max_social = social[j]
			max_social_index = j

	print "The max emotion is: ", max_emotion_index 
	print "The max social is: ", max_social_index 
	
	return max_emotion_index	

def main():
	tone_analyzer = ToneAnalyzerV3Beta(
    	username='994597b6-aacb-4b24-9235-4d8c1aa02a6a',
    	password='zXZydSF5i3VH',
    	version='2016-02-11')
	
	tone_output = json.dumps(tone_analyzer.tone(text=str(sys.argv)), indent=2)
	emoto = analyzeTone(tone_output)


if __name__ == "__main__":
	main()

