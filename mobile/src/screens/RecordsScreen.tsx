import React, { useEffect, useState } from 'react';
import { Alert, Button, FlatList, Text, TouchableOpacity, View } from 'react-native';
import { api } from '../api/client';
import DisclaimerBanner from '../components/DisclaimerBanner';
import * as DocumentPicker from 'expo-document-picker';
import * as ImagePicker from 'expo-image-picker';

type RecordItem = { id: number; recordType: string; providerName: string; recordDate: string; processingStatus: string };

export default function RecordsScreen() {
  const [records, setRecords] = useState<RecordItem[]>([]);
  const [uploading, setUploading] = useState(false);

  const loadRecords = () => api.get('/records').then(r => setRecords(r.data)).catch(() => setRecords([]));
  useEffect(() => { loadRecords(); }, []);

  const uploadFormData = async (uri: string, name: string, type?: string) => {
    try {
      setUploading(true);
      const formData = new FormData();
      formData.append('file', { uri, name, type: type || 'application/octet-stream' } as any);
      await api.post('/records/upload', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });
      await loadRecords();
      Alert.alert('Uploaded', 'Record uploaded successfully. You can now process it.');
    } catch (e) {
      Alert.alert('Upload failed', 'Could not upload record. Check backend URL and auth headers.');
    } finally {
      setUploading(false);
    }
  };

  const pickPdfOrImage = async () => {
    const result = await DocumentPicker.getDocumentAsync({
      type: ['application/pdf', 'image/*'],
      copyToCacheDirectory: true,
      multiple: false,
    });
    if (result.canceled) return;
    const file = result.assets[0];
    await uploadFormData(file.uri, file.name, file.mimeType);
  };

  const pickFromGallery = async () => {
    const permission = await ImagePicker.requestMediaLibraryPermissionsAsync();
    if (!permission.granted) {
      Alert.alert('Permission needed', 'Please allow gallery access to upload images.');
      return;
    }
    const result = await ImagePicker.launchImageLibraryAsync({ mediaTypes: ImagePicker.MediaTypeOptions.Images, quality: 0.8 });
    if (result.canceled) return;
    const asset = result.assets[0];
    await uploadFormData(asset.uri, `gallery-${Date.now()}.jpg`, asset.mimeType);
  };

  const pickFromCamera = async () => {
    const permission = await ImagePicker.requestCameraPermissionsAsync();
    if (!permission.granted) {
      Alert.alert('Permission needed', 'Please allow camera access to upload photos.');
      return;
    }
    const result = await ImagePicker.launchCameraAsync({ mediaTypes: ImagePicker.MediaTypeOptions.Images, quality: 0.8 });
    if (result.canceled) return;
    const asset = result.assets[0];
    await uploadFormData(asset.uri, `camera-${Date.now()}.jpg`, asset.mimeType);
  };

  return (
    <View style={{ flex: 1, padding: 16 }}>
      <Text style={{ fontSize: 22, fontWeight: '700' }}>Records</Text>
      <Text>Upload PDFs, camera photos, or gallery images.</Text>
      <View style={{ gap: 8, marginVertical: 12 }}>
        <Button title={uploading ? 'Uploading...' : 'Upload PDF / Image'} onPress={pickPdfOrImage} disabled={uploading} />
        <Button title={uploading ? 'Uploading...' : 'Take Photo'} onPress={pickFromCamera} disabled={uploading} />
        <Button title={uploading ? 'Uploading...' : 'Upload from Gallery'} onPress={pickFromGallery} disabled={uploading} />
      </View>
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
