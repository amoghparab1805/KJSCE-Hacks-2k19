from django.shortcuts import render
from rest_framework.views import APIView
from rest_framework import generics, viewsets, permissions, status
from django.http import HttpResponse, JsonResponse
from django.contrib.auth.hashers import make_password
from rest_framework.response import Response

from .models import *
from .serializers import *

class EmailSignUp(generics.CreateAPIView):
    serializer_class = EmailSignUpSerializer
    queryset = EmailUser.objects.all()

    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        hashed_password = make_password(serializer.validated_data['password'])
        serializer.validated_data['password'] = hashed_password
        self.perform_create(serializer)
        return Response(serializer.data, status=status.HTTP_201_CREATED)

class GoogleSignUp(APIView):
    def post(self, request, *args, **kwargs):
        data = request.data
        display_name = data['displayName']
        email = data['email']
        photo_url = data['photoURL']
        provider_id = data['providerId']
        try:
            google_user = GoogleUser.objects.get(email=email, provider_id=provider_id)
        except GoogleUser.DoesNotExist:
            google_user = GoogleUser(
                display_name=display_name, 
                email=email,
                photo_url=photo_url,
                provider_id=provider_id
            )
            google_user.save()
        return JsonResponse({
            'Success': 'Success',
            'google_user': google_user.display_name # TODO : Make serializer for user
        })

class PhoneSignUp(APIView):
    def post(self, request, *args, **kwargs):
        data = request.data
        display_name = data['displayName']
        phone_number = data['phone_number']
        provider_id = data['providerId']
        try:
            phone_user = PhoneUser.objects.get(phone_number=phone_number, provider_id=provider_id)
        except PhoneUser.DoesNotExist:
            phone_user = PhoneUser(
                display_name=display_name, 
                phone_number=phone_number,
                provider_id=provider_id
            )
            phone_user.save()
        return JsonResponse({
            'Success': 'Success',
            'phone_user': phone_user.display_name # TODO : Make serializer for user
        })
