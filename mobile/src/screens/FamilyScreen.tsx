import React, { useEffect, useState } from 'react';
import { FlatList, Text, View } from 'react-native';
import { api } from '../api/client';
import DisclaimerBanner from '../components/DisclaimerBanner';

export default function FamilyScreen() {
  const [shares, setShares] = useState<any[]>([]);
  useEffect(() => { api.get('/family/access').then(r => setShares(r.data)).catch(() => setShares([])); }, []);
  return (
    <View style={{ flex: 1, padding: 16 }}>
      <Text style={{ fontSize: 22, fontWeight: '700' }}>Family Sharing</Text>
      <Text>Grant viewer/editor access explicitly per profile.</Text>
      <FlatList data={shares} keyExtractor={(i) => String(i.id)} renderItem={({ item }) => <Text>{item.profileId} → user {item.grantedToUserId} ({item.permissionLevel})</Text>} />
      <DisclaimerBanner />
    </View>
  );
}
