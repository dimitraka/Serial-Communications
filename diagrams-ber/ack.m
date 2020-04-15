function [ noUse ] = arq( )
  noUse = 0;
  fileId = fopen('ARQtimes.txt','r');
  y = fscanf(fileId, '%i');
  fileId = fopen('ARQpackets.txt','r');
  x = fscanf(fileId, '%i');
  x = x';
  y = y';
  
  plot(x,y);
  title('ARQ packets','FontSize', 30);
  xlabel('Number of packets','FontSize', 20);
  ylabel({'Response time of packets','in milliseconds'},'FontSize', 20);
  axis([0 max(y)/2 (max(y)+max(y)/16)]);
endfunction
