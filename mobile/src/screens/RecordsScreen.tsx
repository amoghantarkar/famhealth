import React, { useEffect, useState } from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { api } from '../api/client';
import DisclaimerBanner from '../components/DisclaimerBanner';

type RecordItem = { id: number; recordType: string; providerName: string; recordDate: string; processingStatus: string };

export default function RecordsScreen() {
  const [records, setRecords] = useState<RecordItem[]>([]);
  useEffect(() => { api.get('/records').then(r => setRecords(r.data)).catch(() => setRecords([])); }, []);

  return (
    <View style={{ flex: 1, padding: 16 }}>
      <Text style={{ fontSize: 22, fontWeight: '700' }}>Records</Text>
      <Text>Upload PDFs, camera photos, or gallery images.</Text>
      <DisclaimerBanner />
      <FlatList
        data={records}
        keyExtractor={(item) => String(item.id)}
        renderItem={({ item }) => (
          <TouchableOpacity style={{ padding: 12, borderBottomWidth: 1, borderColor: '#ececec' }}>
            <Text style={{ fontWeight: '600' }}>{item.recordType} • {item.providerName}</Text>
            <Text>{item.recordDate} • {item.processingStatus}</Text>
            <Text style={{ color: '#777' }}>Extracted from report / Needs review / Verified by you</Text>
          </TouchableOpacity>
        )}
      />
    </View>
  );
}
