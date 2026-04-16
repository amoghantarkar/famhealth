import React from 'react';
import { Text, View } from 'react-native';

export default function SettingsScreen() {
  return (
    <View style={{padding:16}}>
      <Text style={{fontWeight:'700'}}>Privacy & Settings</Text>
      <Text>Delete account workflow and privacy policy links go here.</Text>
      <Text>Informational only. Not medical advice.</Text>
    </View>
  );
}
